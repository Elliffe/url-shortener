package com.example.urlshortener.dao;

import com.example.urlshortener.model.Stat;
import com.example.urlshortener.model.Url;

public interface StatDao {

    void insertStat(Stat stat);
    Object getStats(Url url);
}
