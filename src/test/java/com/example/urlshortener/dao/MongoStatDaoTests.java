package com.example.urlshortener.dao;

import com.example.urlshortener.model.Stat;
import com.example.urlshortener.model.Url;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoStatDaoTests {

    @Autowired
    private MongoStatDao mongoStatDao;
    @Autowired
    private MongoStatRepository mongoStatRepository;
    private final Stat testStat = new Stat(0);
    private ObjectMapper objectMapper = new ObjectMapper();

    @After
    public void tearDown() {
        mongoStatRepository.deleteAll();
    }

    @Test
    public void testMongoInsert() {
        Assert.assertTrue(mongoStatDao.insertStat(testStat));
    }

    @Test
    public void testMongoInsertDuplicateId() {
        mongoStatDao.insertStat(testStat);
        Assert.assertTrue(mongoStatDao.insertStat(testStat));
    }

    @Test
    public void testMongoRead() throws JsonProcessingException {
        Object expected = new Object() {
            public final String originalUrl = "http://www.example.com";
            public final int totalClicks = 1;
            public final int last7Days = 1;
            public final int last30Days = 1;
        };
        String expectedJson = objectMapper.writeValueAsString(expected);

        boolean statInserted = mongoStatDao.insertStat(testStat);
        Url url = new Url(0, "http://www.example.com");
        Object stats = mongoStatDao.getStats(url);

        String resultJson = objectMapper.writeValueAsString(stats);
        Assert.assertEquals(expectedJson, resultJson);
    }
}
