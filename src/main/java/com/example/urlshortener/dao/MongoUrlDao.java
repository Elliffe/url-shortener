package com.example.urlshortener.dao;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.IdService;
import com.mongodb.DuplicateKeyException;
import org.springframework.stereotype.Repository;

@Repository("MongoUrlDao")
public class MongoUrlDao implements UrlDao {

    private final MongoUrlRepository mongoUrlRepository;
    private final IdService idService;

    public MongoUrlDao(MongoUrlRepository mongoUrlRepository, IdService idService) {
        this.mongoUrlRepository = mongoUrlRepository;
        this.idService = idService;
    }

    @Override
    public boolean insertUrl(Url url) {
        try {
            mongoUrlRepository.save(url);
            return true;
        } catch (DuplicateKeyException e) {
            // There should never be a situation where a duplicate id is inserted as the IdRange Service is handling this,
            // however; if it does happen, get a new range to prevent subsequent inserts within the current range failing.
            idService.getNewRange();
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
