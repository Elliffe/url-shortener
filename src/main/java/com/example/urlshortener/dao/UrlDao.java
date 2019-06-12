package com.example.urlshortener.dao;

import com.example.urlshortener.model.Url;

public interface UrlDao {

    boolean insertUrl(Url url);
    Url getUrl(long id);
}
