package com.example.urlshortener.dao;

import com.example.urlshortener.model.Url;

public interface UrlDao {

    Url insertUrl(Url url);
}
