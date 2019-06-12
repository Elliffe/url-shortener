package com.example.urlshortener.service;

import com.example.urlshortener.dao.UrlDao;
import com.example.urlshortener.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class UrlService {

    private final UrlDao urlDao;

    @Autowired
    public UrlService(@Qualifier("MongoUrlDao") UrlDao urlDao) {
        this.urlDao = urlDao;
    }

    public boolean insertUrl(Url url) {
        if(isValidUrl(url.getUrl())) {
            return urlDao.insertUrl(url);
        } else {
            return false;
        }
    }

    public Url getUrl(long id) {
        return urlDao.getUrl(id);
    }

    /* Returns true if url is valid */
    private static boolean isValidUrl(String url)
    {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
}
