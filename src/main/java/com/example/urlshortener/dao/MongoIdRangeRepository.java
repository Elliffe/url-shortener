package com.example.urlshortener.dao;

import com.example.urlshortener.model.IdRange;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoIdRangeRepository extends MongoRepository<IdRange, String> {

    IdRange findTopByOrderByIdDesc();
}
