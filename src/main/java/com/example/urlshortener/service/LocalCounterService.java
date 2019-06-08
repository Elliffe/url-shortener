package com.example.urlshortener.service;

import org.springframework.stereotype.Repository;

@Repository("local")
public class LocalCounterService implements CounterService {

    private static int counter = 0;

    @Override
    public int getCounter() {
        counter++;
        return counter;
    }
}
