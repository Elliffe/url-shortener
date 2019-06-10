package com.example.urlshortener.dao;

import com.example.urlshortener.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUrlRepository extends MongoRepository<Url, String> {

    Url findById(long id);
}
