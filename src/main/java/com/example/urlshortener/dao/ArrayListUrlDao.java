package com.example.urlshortener.dao;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.CounterService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("ArrayListDao")
public class ArrayListUrlDao implements UrlDao {

    private static List<Url> DB = new ArrayList<>();
    private final CounterService counterService;

    public ArrayListUrlDao(@Qualifier("local") CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    public Url insertUrl(Url url){
        int id = this.counterService.getCounter();
        DB.add(new Url(id, url.getUrl()));
        return url;
    }
}
