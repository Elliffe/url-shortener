package com.example.urlshortener.service;

import com.example.urlshortener.dao.StatDao;
import com.example.urlshortener.model.Stat;
import com.example.urlshortener.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StatService {

    private final StatDao statDao;

    @Autowired
    public StatService(@Qualifier("MongoStatDao") StatDao statDao) {
        this.statDao = statDao;
    }

    public void insertStat(Stat stat) {
        statDao.insertStat(stat);
    }

    public Object getStats(Url url) {
        return statDao.getStats(url);
    }
}
