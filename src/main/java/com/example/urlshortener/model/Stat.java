package com.example.urlshortener.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Stat {

    @Id
    private String id;
    private long urlId;

    public Stat(long urlId){
        this.urlId = urlId;
    }
}
