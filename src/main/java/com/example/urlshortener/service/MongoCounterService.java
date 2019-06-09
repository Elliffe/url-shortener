package com.example.urlshortener.service;

import com.example.urlshortener.dao.MongoIdRangeRepository;
import com.example.urlshortener.model.IdRangeMongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("mongoCounterService")
public class MongoCounterService implements CounterService {

    private final MongoIdRangeRepository idRangeRepository;
    private static long counter;
    private IdRangeMongo idRange;
    private final int rangeSize;

    public MongoCounterService(MongoIdRangeRepository idRangeRepository, @Value("${rangeSize}") final int rangeSize) {
        this.idRangeRepository = idRangeRepository;
        this.rangeSize = rangeSize;
        getNewRange();
    }

    @Override
    public long getCounter() {
        checkRangeLimitReached();
        return counter++;
    }

    private void checkRangeLimitReached() {
        if(counter == idRange.getEndOfRange()) {
            getNewRange();
        }
    }

    private void getNewRange() {
        while (true) {
            try {
                // Create a new range of ids based on the newest range currently in the DB.
                // Attempt to save the new range to the DB, if it fails then another service has already created it,
                // therefore we should move on to the next range.
                IdRangeMongo newestRangeInDb = idRangeRepository.findTopByOrderByIdDesc();
                IdRangeMongo newRange = newestRangeInDb == null ? new IdRangeMongo(0, rangeSize - 1) :
                        new IdRangeMongo(newestRangeInDb.getEndOfRange() + 1, newestRangeInDb.getEndOfRange() + rangeSize);

                idRange = idRangeRepository.save(newRange);
                counter = idRange.getStartOfRange();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
