package com.example.urlshortener.dao;

import com.example.urlshortener.model.IdRange;
import com.example.urlshortener.model.Stat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoIdRangeDaoTests {

    @Autowired
    private MongoIdRangeDao mongoIdRangeDao;
    @Autowired
    private MongoIdRangeRepository mongoIdRangeRepository;
    private final Stat testStat = new Stat(0);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void tearDown() {
        mongoIdRangeRepository.deleteAll();
    }

    @Test
    public void testMongoGetRange() {
        IdRange expectedRange = new IdRange(0, 999);
        IdRange actualRange = mongoIdRangeDao.getIdRange();
        Assert.assertEquals(expectedRange.getStartOfRange(), actualRange.getStartOfRange());
        Assert.assertEquals(expectedRange.getEndOfRange(), actualRange.getEndOfRange());
    }

    @Test
    public void testMongoGetSecondRange() {
        IdRange expectedRange = new IdRange(1000, 1999);
        IdRange tst = mongoIdRangeDao.getIdRange();
        IdRange actualRange = mongoIdRangeDao.getIdRange();

        Assert.assertEquals(expectedRange.getStartOfRange(), actualRange.getStartOfRange());
        Assert.assertEquals(expectedRange.getEndOfRange(), actualRange.getEndOfRange());
    }
}
