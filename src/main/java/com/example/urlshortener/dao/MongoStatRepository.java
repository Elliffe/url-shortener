package com.example.urlshortener.dao;

import com.example.urlshortener.model.Stat;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoStatRepository extends MongoRepository<Stat, String> {

    List<Stat> findByUrlId(long urlId);

    @Query("{_id:{$gt: ?0}, urlId: ?1}")
    List<Stat> findByUrlIdAndDate(ObjectId idSince, long urlId);
}
