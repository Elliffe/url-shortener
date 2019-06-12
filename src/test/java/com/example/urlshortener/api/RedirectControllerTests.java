package com.example.urlshortener.api;

import com.example.urlshortener.dao.MongoUrlRepository;
import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.UrlService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RedirectControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MongoUrlRepository mongoUrlRepository;
    @Autowired
    private UrlService urlService;

    @After
    public void tearDown() {
        mongoUrlRepository.deleteAll();
    }

    @Test
    public void shouldRedirectValidUrl() throws Exception {
        Url url = new Url(0, "http://www.example.com");
        urlService.insertUrl(url);

        this.mockMvc.perform(
                get("/a"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url.getUrl()))
                .andReturn();

        Url url2 = new Url(1, "http://www.test.com");
        urlService.insertUrl(url2);
        this.mockMvc.perform(
                get("/b"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url2.getUrl()))
                .andReturn();
    }
}
