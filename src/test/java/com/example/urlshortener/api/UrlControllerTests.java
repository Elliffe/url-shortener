package com.example.urlshortener.api;

import com.example.urlshortener.dao.MongoUrlRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UrlControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MongoUrlRepository mongoUrlRepository;

    @After
    public void tearDown() {
        mongoUrlRepository.deleteAll();
    }

    @Test
    public void shouldReturnShortUrls() throws Exception {
        Object randomObj = new Object() {
            public final String url = "http://www.example.com";
        };

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(randomObj);


        MvcResult result = this.mockMvc.perform(
            post("/api/v1/shorten")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk())
            .andReturn();

        String body = result.getResponse().getContentAsString();
        Assert.assertEquals(body, "http://localhost:8080/a");

        result = this.mockMvc.perform(
                post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        body = result.getResponse().getContentAsString();
        Assert.assertEquals(body, "http://localhost:8080/b");
    }

    @Test
    public void shouldReturnLongUrl() throws Exception {
        String longUrl = "http://www.example.com";
        Object obj = new Object() {
            public final String url = longUrl;
        };

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(obj);


        MvcResult result = this.mockMvc.perform(
                post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();

        String idStr = body.substring(body.lastIndexOf('/') + 1);

        this.mockMvc.perform(
                get("/"+idStr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl(longUrl))
                .andReturn();


        // Submit a second URL and test the returned short URL
        String longUrl2 = "http://www.google.com";
        Object obj2 = new Object() {
            public final String url = longUrl2;
        };

        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(obj2);


        result = this.mockMvc.perform(
                post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        body = result.getResponse().getContentAsString();

        idStr = body.substring(body.lastIndexOf('/') + 1);

        this.mockMvc.perform(
                get("/"+idStr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl(longUrl2))
                .andReturn();
    }

    @Test
    public void shouldRejectInjectionUrls() throws Exception {
        // Test for character "{"
        Object obj = new Object() {
            public final String url = "{";
        };

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(obj);


        this.mockMvc.perform(
                post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(400))
                .andReturn();

        // Test for character "}"
        obj = new Object() {
            public final String url = "}";
        };

        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(obj);


        this.mockMvc.perform(
                post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(400))
                .andReturn();

        // Test for character "\"
        obj = new Object() {
            public final String url = "\\";
        };

        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(obj);


        this.mockMvc.perform(
                post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    public void shouldRejectInvalidUrl() throws Exception {
        // Test for character "{"
        Object obj = new Object() {
            public final String url = "example.com";
        };

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(obj);


        this.mockMvc.perform(
                post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(400))
                .andReturn();
    }
}
