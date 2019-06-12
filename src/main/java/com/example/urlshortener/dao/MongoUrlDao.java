package com.example.urlshortener.dao;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.CounterService;
import com.mongodb.DuplicateKeyException;
import org.springframework.stereotype.Repository;

@Repository("MongoUrlDao")
public class MongoUrlDao implements UrlDao {

    private final MongoUrlRepository mongoUrlRepository;
    private final CounterService counterService;

    public MongoUrlDao(MongoUrlRepository mongoUrlRepository, CounterService counterService) {
        this.mongoUrlRepository = mongoUrlRepository;
        this.counterService = counterService;
    }

    @Override
    public boolean insertUrl(Url url) {
        try {
            mongoUrlRepository.save(url);
            return true;
        } catch (DuplicateKeyException e) {
            // There should never be a situation where a duplicate id is inserted as the IdRange Dao is handling this,
            // however; if it does happen, get a new range to prevent subsequent inserts within the current range failing.
            counterService.getNewRange();
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Url getUrl(long id) {
        return mongoUrlRepository.findById(id);
    }
}
