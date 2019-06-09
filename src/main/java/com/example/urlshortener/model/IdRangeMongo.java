package com.example.urlshortener.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("idRange")
public class IdRangeMongo implements IdRange {

    @Id
    private String id;

    @Indexed(unique = true)
    private long startOfRange;
    @Indexed(unique = true)
    private long endOfRange;

    public IdRangeMongo(long startOfRange, long endOfRange) {
        this.startOfRange = startOfRange;
        this.endOfRange = endOfRange;
    }

    public long getStartOfRange() {
        return startOfRange;
    }

    public long getEndOfRange() {
        return endOfRange;
    }
}
