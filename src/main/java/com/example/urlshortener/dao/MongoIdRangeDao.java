package com.example.urlshortener.dao;

import com.example.urlshortener.model.IdRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("MongoIdRangeDao")
public class MongoIdRangeDao implements IdRangeDao {

    private final MongoIdRangeRepository mongoIdRangeRepository;
    private int rangeSize;

    public MongoIdRangeDao(MongoIdRangeRepository mongoIdRangeRepository, @Value("${rangeSize}") final int rangeSize) {
        this.mongoIdRangeRepository = mongoIdRangeRepository;
        this.rangeSize = rangeSize;
    }

    @Override
    public IdRange getIdRange() {
        IdRange idRange;
        int retries = 1000;
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
            }
        }
        return null;
    }
}
