package com.example.urlshortener.service;

import com.example.urlshortener.dao.MongoStatRepository;
import com.example.urlshortener.model.Url;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatServiceTests {

    @Autowired
    private StatService statService;

    @Autowired
    private UrlService urlService;

    @Autowired
    private MongoStatRepository mongoStatRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    private ObjectMapper objectMapper = new ObjectMapper();
    @Before
    public void tearDown() {
        mongoStatRepository.deleteAll();
    }


    @Test
    public void testGetStats() throws JsonProcessingException {
        Object expected = new Object() {
            public final String originalUrl = "http://www.example.com";
            public final int totalClicks = 0;
            public final int last7Days = 0;
            public final int last30Days = 0;
        };
        String expectedJson = objectMapper.writeValueAsString(expected);

        Url url = new Url(0, "http://www.example.com");
        urlService.insertUrl(url);
        Object stats = statService.getStats(url);

        String resultJson = objectMapper.writeValueAsString(stats);

        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void testGetStatsExtended() throws JsonProcessingException {
        Object expected = new Object() {
            public final String originalUrl = "http://www.example.com";
            public final int totalClicks = 4;
            public final int last7Days = 2;
            public final int last30Days = 3;
        };
        String expectedJson = objectMapper.writeValueAsString(expected);

        Url url = new Url(0, "http://www.example.com");
        urlService.insertUrl(url);

        // Insert one Stat now
        LocalDateTime now = LocalDateTime.now();
        Date convertedDateNow = java.util.Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        ObjectId idSinceNow = new ObjectId(convertedDateNow);
        DBObject objectToSaveNow = BasicDBObjectBuilder.start()
                .add("_id", idSinceNow)
                .add("urlId", 0)
                .get();

        mongoTemplate.save(objectToSaveNow, "stat");

        // Insert one Stat 6 days ago
        LocalDateTime sixDaysAgo = LocalDateTime.now().minusDays(6);
        Date convertedDate6DaysAgo = java.util.Date.from(sixDaysAgo.atZone(ZoneId.systemDefault()).toInstant());
        ObjectId idSince6DaysAgo = new ObjectId(convertedDate6DaysAgo);
        DBObject objectToSave6DaysAgo = BasicDBObjectBuilder.start()
                .add("_id", idSince6DaysAgo)
                .add("urlId", 0)
                .get();

        mongoTemplate.save(objectToSave6DaysAgo, "stat");

        // Insert one Stat for 29 days ago
        LocalDateTime twentyNineDaysAgo = LocalDateTime.now().minusDays(29);
        Date convertedDate29DaysAgo = java.util.Date.from(twentyNineDaysAgo.atZone(ZoneId.systemDefault()).toInstant());
        ObjectId idSince29DaysAgo = new ObjectId(convertedDate29DaysAgo);
        DBObject objectToSave29DaysAgo = BasicDBObjectBuilder.start()
                .add("_id", idSince29DaysAgo)
                .add("urlId", 0)
                .get();

        mongoTemplate.save(objectToSave29DaysAgo, "stat");

        // Insert one Stat for 31 days ago
        LocalDateTime thirtyOneDaysAgo = LocalDateTime.now().minusDays(31);
        Date convertedDate31DaysAgo = java.util.Date.from(thirtyOneDaysAgo.atZone(ZoneId.systemDefault()).toInstant());
        ObjectId idSince31DaysAgo = new ObjectId(convertedDate31DaysAgo);
        DBObject objectToSave31DaysAgo = BasicDBObjectBuilder.start()
                .add("_id", idSince31DaysAgo)
                .add("urlId", 0)
                .get();

        mongoTemplate.save(objectToSave31DaysAgo, "stat");

        Object stats = statService.getStats(url);
        String resultJson = objectMapper.writeValueAsString(stats);

        Assert.assertEquals(expectedJson, resultJson);
    }
}
