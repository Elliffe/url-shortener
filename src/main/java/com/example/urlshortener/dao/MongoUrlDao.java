package com.example.urlshortener.dao;

import com.example.urlshortener.model.Url;
import org.springframework.stereotype.Repository;

@Repository("MongoUrlDao")
public class MongoUrlDao implements UrlDao {

    private final MongoUrlRepository mongoUrlRepository;

    public MongoUrlDao(MongoUrlRepository mongoUrlRepository) {
        this.mongoUrlRepository = mongoUrlRepository;
    }

    @Override
    public Url insertUrl(Url url) {
        return mongoUrlRepository.save(url);
    }

    @Override
    public Url getUrl(long id) {
        return mongoUrlRepository.findById(id);
    }
}
