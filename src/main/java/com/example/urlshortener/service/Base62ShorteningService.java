package com.example.urlshortener.service;

import com.example.urlshortener.model.Url;
import org.springframework.stereotype.Repository;

@Repository("Base62")
public class Base62ShorteningService implements ShorteningService {

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public String encodeUrl(Url url) {
        long id = url.getId();
        StringBuilder sb = new StringBuilder();

        do {
            long rem = id % 62;
            sb.append(BASE62.charAt((int)rem));
            id /= 62;
        } while(id != 0);

        return sb.reverse().toString();

    }

    @Override
    public long decodeId(String encodedId) {
        long decodedId = 0;
        for(int i = 0; i < encodedId.length(); i++) {
            decodedId = decodedId * 62 + BASE62.indexOf("" + encodedId.charAt(i));
        }
        return decodedId;
    }
}
