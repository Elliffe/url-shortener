package com.example.urlshortener.service;

import java.net.URI;

public interface ShorteningService {

    String encodeUrl(URI longUrl);
    String decodeUrl(URI longUrl);
}
