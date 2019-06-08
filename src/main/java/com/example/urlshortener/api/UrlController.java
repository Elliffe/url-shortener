package com.example.urlshortener.api;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.ShorteningService;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("")
@RestController
public class UrlController {

    private final UrlService urlService;
    private final ShorteningService shorteningService;
    @Value("${hostname}")
    private String hostname;

    @Autowired
    public UrlController(UrlService urlService, @Qualifier("Base62") ShorteningService shorteningService) {
        this.urlService = urlService;
        this.shorteningService = shorteningService;
    }

    @PostMapping
    public Map<String, String> insertUrl(@RequestBody Url url) {
        Url shortenedUrl = urlService.insertUrl(url);
        String shortUrl = hostname.concat("/").concat(shorteningService.encodeUrl(url.getUrl()));

        HashMap<String, String> map = new HashMap<>();
        map.put("long", url.getUrl().toString());
        map.put("short", shortUrl);
        return map;
    }
}
