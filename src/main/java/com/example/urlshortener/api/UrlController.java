package com.example.urlshortener.api;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.ShorteningService;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public Map<String, String> insertUrl(@RequestBody Url request) {
        Url url = urlService.insertUrl(request);
        String shortUrl = hostname.concat("/").concat(shorteningService.encodeUrl(url));

        HashMap<String, String> map = new HashMap<>();
        map.put("long", url.getUrl().toString());
        map.put("short", shortUrl);
        return map;
    }

    @GetMapping(path = "{id}")
    public ModelAndView method(@PathVariable("id") String id) {
        long urlId = shorteningService.decodeId(id);
        Url fullUrl = urlService.getUrl(urlId);
        return new ModelAndView("redirect:" + fullUrl.getUrl());
    }
}
