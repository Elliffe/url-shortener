package com.example.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;

public class Url {

    private final int id;
    private final URI url;

    public Url(int id, @JsonProperty("url") URI url) {
        this.id = id;
        this.url = url;
    }

    public URI getUrl() {
        return url;
    }
}
