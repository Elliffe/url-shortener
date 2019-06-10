package com.example.urlshortener.dao;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.CounterService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("ArrayListDao")
public class ArrayListUrlDao implements UrlDao {

    private static List<Url> DB = new ArrayList<>();
    private final CounterService counterService;

    public ArrayListUrlDao(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    public Url insertUrl(Url url){
        DB.add(url);
        return url;
    }

    @Override
    public Url getUrl(long id) {
        for (Url url : DB) {
            if(url.getId() == id) {
                return url;
            }
        }
        return null;
    }
}
