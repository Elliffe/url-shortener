package com.example.urlshortener.api;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.IdService;
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
    private final IdService idService;
    private final UrlService urlService;

    @Value("${BASE_URL}")
    private String BASE_URL;

    @Autowired
    public UrlController(@Qualifier("Base62") ShorteningService shorteningService,
                         IdService idService,
                         UrlService urlService) {
        this.shorteningService = shorteningService;
        this.idService = idService;
        this.urlService = urlService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> insertUrl(@RequestBody Url request) {
        Long id = idService.getId();
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

        Url url = new Url(id, request.getUrl());

        // If the URL insert fails the URL was likely invalid
        if(!urlService.insertUrl(url)) {
            return new ResponseEntity<>("Invalid URL", HttpStatus.BAD_REQUEST);
        }

        // Append the Base62 encoded ID to the BASE_URL
        String shortUrl = BASE_URL.concat("/").concat(shorteningService.encodeUrl(url));

        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }
}
