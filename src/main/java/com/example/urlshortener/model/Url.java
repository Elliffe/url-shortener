package com.example.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class Url {

    @Id
    private final long id;
    private final String url;

    public Url(@JsonProperty("id") long id, @JsonProperty("url") String url) {
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public long getId() {
        return id;
    }
}
