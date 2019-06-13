package com.example.urlshortener.api;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.ShorteningService;
import com.example.urlshortener.service.StatService;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("api/v1/stats")
@Controller
public class StatsController {

    private final ShorteningService shorteningService;
    private final UrlService urlService;
    private final StatService statService;

    public StatsController(@Qualifier("Base62")ShorteningService shorteningService,
                           UrlService urlService,
                           StatService statService) {
        this.shorteningService = shorteningService;
        this.urlService = urlService;
        this.statService = statService;
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Object> method(@PathVariable("id") String longId) {
        long shortId = shorteningService.decodeId(longId);
        Url fullUrl = urlService.getUrl(shortId);

        if(fullUrl == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found");
        }

        return new ResponseEntity<>(statService.getStats(fullUrl), HttpStatus.OK);
    }
}
