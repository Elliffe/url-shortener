package com.example.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;

public class Url {

    private final long id;
    private final URI url;

    public Url(@JsonProperty("id") long id, @JsonProperty("url") URI url) {
        this.url = url;
        this.id = id;
    }

    public URI getUrl() {
        return url;
    }

    public long getId() {
        return id;
    }
}
