package com.example.urlshortener.service;

import com.example.urlshortener.dao.UrlDao;
import com.example.urlshortener.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    private final UrlDao urlDao;

    @Autowired
    public UrlService(@Qualifier("MongoUrlDao") UrlDao urlDao) {
        this.urlDao = urlDao;
    }

    public Url insertUrl(Url url) {
        return urlDao.insertUrl(url);
    }

    public Url getUrl(long id) {
        return urlDao.getUrl(id);
    }
}
