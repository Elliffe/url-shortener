package com.example.urlshortener.api;

import com.example.urlshortener.model.Stat;
import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.ShorteningService;
import com.example.urlshortener.service.StatService;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("")
@RestController
public class RedirectController {


    private final ShorteningService shorteningService;
    private final StatService statService;
    private final UrlService urlService;

    public RedirectController(@Qualifier("Base62") ShorteningService shorteningService,
                              StatService statService,
                              UrlService urlService) {
        this.shorteningService = shorteningService;
        this.statService = statService;
        this.urlService = urlService;
    }

    @GetMapping(path = "{id}")
    public ModelAndView method(@PathVariable("id") String id) {
        long urlId = shorteningService.decodeId(id);
        Url fullUrl = urlService.getUrl(urlId);
        if(fullUrl == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL Not Found"); }

        Stat stat = new Stat(fullUrl.getId());
        statService.insertStat(stat);
        return new ModelAndView("redirect:" + fullUrl.getUrl());
    }
}
