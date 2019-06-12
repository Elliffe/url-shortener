package com.example.urlshortener.dao;

import com.example.urlshortener.model.Url;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoUrlDaoTests {

    @Autowired
    private MongoUrlDao mongoUrlDao;
    @Autowired
    private MongoUrlRepository mongoUrlRepository;
    private final Url testUrl = new Url(0, "http://www.example.com");

    @After
    public void tearDown() {
        mongoUrlRepository.deleteAll();
    }

    @Test
    public void testMongoInsert() {
        Assert.assertTrue(mongoUrlDao.insertUrl(testUrl));
    }

    @Test
    public void testMongoInsertDuplicateId() {
        mongoUrlDao.insertUrl(testUrl);
        Assert.assertFalse(mongoUrlDao.insertUrl(testUrl));
    }

    @Test
    public void testMongoRead() {
        mongoUrlDao.insertUrl(testUrl);
        Url url = mongoUrlDao.getUrl(0);
        Assert.assertEquals(testUrl.getUrl(), url.getUrl());
    }
}
