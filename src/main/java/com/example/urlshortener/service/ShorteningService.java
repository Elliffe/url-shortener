package com.example.urlshortener.service;

import com.example.urlshortener.model.Url;

public interface ShorteningService {

    String encodeUrl(Url url);
    long decodeId(String encodedId);
}
