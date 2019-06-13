package com.example.urlshortener.service;

import com.example.urlshortener.dao.IdRangeDao;
import com.example.urlshortener.model.IdRange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class IdService {

    private static long counter;
    private static IdRange idRange;
    private final IdRangeDao idRangeDao;

    public IdService(@Qualifier("MongoIdRangeDao") IdRangeDao idRangeDao) {
        this.idRangeDao = idRangeDao;
        idRange = idRangeDao.getIdRange();
        if(idRange != null) { counter = idRange.getStartOfRange(); }
    }

    public Long getId() {
        // If the IdRange is null then attempt to get a new range. If the new range is null also
        // then there is likely an issue with the DB, send the client a 503
        if(idRange == null) {
            getNewRange();
        }
        if(idRange == null) {
            return null;
        }

        if(counter == idRange.getEndOfRange()) {
            try {
                getNewRange();
            } catch (Exception e) {
                return null;
            }
        }

        return counter++;
    }

    public void getNewRange() {
        idRange = idRangeDao.getIdRange();
        counter = idRange.getStartOfRange();
    }
}
