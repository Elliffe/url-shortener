package com.example.urlshortener.service;

import com.example.urlshortener.dao.IdRangeDao;
import com.example.urlshortener.model.IdRange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

    private static long counter = -1;
    private static IdRange idRange;
    private final IdRangeDao idRangeDao;

    public CounterService(@Qualifier("MongoIdRangeDao") IdRangeDao idRangeDao) {
        this.idRangeDao = idRangeDao;
        idRange = idRangeDao.getIdRange();
        if(idRange != null) { counter = idRange.getStartOfRange(); }
    }

    public Long getCounter() {
        if(idRange == null) { return null; }
        if(counter == idRange.getEndOfRange() || counter < 0) {
            idRange = idRangeDao.getIdRange();
            if(idRange == null) { return null; }
            counter = idRange.getStartOfRange();
        }
        return counter++;
    }

    private void checkRangeLimitReached() {
        if(counter == idRange.getEndOfRange() || counter < 0) {
            idRange = idRangeDao.getIdRange();
            counter = idRange.getStartOfRange();
        }
    }
}
