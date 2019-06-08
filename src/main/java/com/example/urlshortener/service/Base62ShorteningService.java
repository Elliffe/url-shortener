package com.example.urlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.net.URI;

@Repository("Base62")
public class Base62ShorteningService implements ShorteningService {

    private final CounterService counterService;

    @Autowired
    public Base62ShorteningService(@Qualifier("local") CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    public String encodeUrl(URI longUrl) {
        String base62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        int id = this.counterService.getCounter();

        StringBuilder sb = new StringBuilder();
        while (id != 0) {
            sb.append(base62.charAt(id % 62));
            id /= 62;
        }
        return sb.reverse().toString();
    }

    @Override
    public String decodeUrl(URI longUrl) {
        return null;
    }
}
