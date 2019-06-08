package com.example.urlshortener.Service;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.Base62ShorteningService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Base62ShorteningServiceTests {

    @Autowired
    private Base62ShorteningService base62ShorteningService;

    @Test
    public void testEncodeUrl() {
        URI uri = URI.create("http://www.example.com");
        Url url = new Url(1, uri);
        String encodedUrl = base62ShorteningService.encodeUrl(url);
        Assert.assertEquals("b", encodedUrl);
    }

    @Test
    public void testEncodeUrlIntegerOverflow() {
        URI uri = URI.create("http://www.example.com");
        Url url = new Url(2147483648L, uri);
        String encodedUrl = base62ShorteningService.encodeUrl(url);
        Assert.assertEquals("cvuMLc", encodedUrl);
    }

    @Test
    public void testEncodeUrlLongLimit() {
        URI uri = URI.create("http://www.example.com");
        Url url = new Url(9223372036854775807L, uri);
        String encodedUrl = base62ShorteningService.encodeUrl(url);
        Assert.assertEquals("k9viXaIfiWh", encodedUrl);
    }

    @Test
    public void testDecodeUrl() {
        long decodedUrl = base62ShorteningService.decodeId("b");
        Assert.assertEquals(1, decodedUrl);
    }

    @Test
    public void testDecodeUrlIntegerOverflow() {
        long decodedUrl = base62ShorteningService.decodeId("cvuMLc");
        Assert.assertEquals(2147483648L, decodedUrl);
    }

    @Test
    public void testDecodeUrlLongLimit() {
        long decodedUrl = base62ShorteningService.decodeId("k9viXaIfiWh");
        Assert.assertEquals(9223372036854775807L, decodedUrl);
    }


}
