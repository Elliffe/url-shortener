package com.example.urlshortener.dao;

import com.example.urlshortener.model.IdRangeMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoIdRangeRepository extends MongoRepository<IdRangeMongo, String> {

    IdRangeMongo findTopByOrderByIdDesc();
}
