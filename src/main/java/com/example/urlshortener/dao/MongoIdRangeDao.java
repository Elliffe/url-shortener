package com.example.urlshortener.dao;

import com.example.urlshortener.model.IdRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository("MongoIdRangeDao")
public class MongoIdRangeDao implements IdRangeDao {

    private final MongoIdRangeRepository mongoIdRangeRepository;
    private int rangeSize;

    public MongoIdRangeDao(MongoIdRangeRepository mongoIdRangeRepository, @Value("${RANGE_SIZE}") final int rangeSize) {
        this.mongoIdRangeRepository = mongoIdRangeRepository;
        this.rangeSize = rangeSize;
    }

    @Override
    public IdRange getIdRange() {
        IdRange idRange;

        // If 2 or more instances of the service are trying to reserve ID blocks in tandem 1 will succeed and the rest
        // will fail, retrying after 1 second should give enough time for the DB to become available again. If the
        // insert is still failing after 30 seconds then there is likely an issue.
        int retries = 30;
        while (retries > 0) {
            retries--;
            try {
                // Create a new range of ids based on the newest range currently in the DB.
                // Attempt to save the new range to the DB, if it fails then another service has already created it,
                // therefore we should move on to the next range.
                IdRange newestRangeInDb = mongoIdRangeRepository.findTopByOrderByIdDesc();
                IdRange newRange = newestRangeInDb == null ? new IdRange(0, rangeSize - 1) :
                        new IdRange(newestRangeInDb.getEndOfRange() + 1, newestRangeInDb.getEndOfRange() + rangeSize);

                idRange = mongoIdRangeRepository.save(newRange);
                return idRange;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException ex) { ex.printStackTrace(); }
            }
        }
        return null;
    }
}
