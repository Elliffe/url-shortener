package com.example.urlshortener.service;

import org.springframework.stereotype.Repository;

@Repository("local")
public class LocalCounterService implements CounterService {

    private static long counter = 0;

    @Override
    public long getCounter() {
        counter++;
        return counter;
    }
}
