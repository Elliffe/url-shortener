package com.example.urlshortener.api;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.CounterService;
import com.example.urlshortener.service.ShorteningService;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("")
@RestController
public class UrlController {

    private final UrlService urlService;
    private final ShorteningService shorteningService;
    private final CounterService counterService;
    @Value("${hostname}")
    private String hostname;

    @Autowired
    public UrlController(UrlService urlService,
                         @Qualifier("Base62") ShorteningService shorteningService,
                         CounterService counterService) {
        this.urlService = urlService;
        this.shorteningService = shorteningService;
        this.counterService = counterService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> insertUrl(@RequestBody Url request, HttpServletResponse response) {
        Long id = counterService.getCounter();
        if(id == null) { return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE); }

        Url url = new Url(id, request.getUrl());
        urlService.insertUrl(url);
        String shortUrl = hostname.concat("/").concat(shorteningService.encodeUrl(url));

        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ModelAndView method(@PathVariable("id") String id) {
        long urlId = shorteningService.decodeId(id);
        Url fullUrl = urlService.getUrl(urlId);
        if(fullUrl == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL Not Found"); }

        return new ModelAndView("redirect:" + fullUrl.getUrl());
    }
}
