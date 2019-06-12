package com.example.urlshortener.api;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.CounterService;
import com.example.urlshortener.service.ShorteningService;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("api/v1/shorten")
@RestController
public class UrlController {

    private final ShorteningService shorteningService;
    private final CounterService counterService;
    private final UrlService urlService;

    @Value("${hostname}")
    private String hostname;

    @Autowired
    public UrlController(@Qualifier("Base62") ShorteningService shorteningService,
                         CounterService counterService,
                         UrlService urlService) {
        this.shorteningService = shorteningService;
        this.counterService = counterService;
        this.urlService = urlService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> insertUrl(@RequestBody Url request, @RequestHeader HttpHeaders headers, HttpServletResponse response) {
        Long id = counterService.getCounter();
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

        Url url = new Url(id, request.getUrl());
        if(!urlService.insertUrl(url)) {
            return new ResponseEntity<>("Invalid URL", HttpStatus.BAD_REQUEST);
        }
        String shortUrl = hostname.concat("/").concat(shorteningService.encodeUrl(url));

        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }
}
