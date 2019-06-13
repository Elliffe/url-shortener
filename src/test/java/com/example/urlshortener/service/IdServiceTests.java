package com.example.urlshortener.service;

import com.example.urlshortener.dao.MongoIdRangeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdServiceTests {
    @Value("${RANGE_SIZE}")
    private int rangeSize;

    @Autowired
    private IdService idService;


    @Autowired
    private MongoIdRangeRepository mongoIdRangeRepository;

    @Before
    public void tearDown() {
        mongoIdRangeRepository.deleteAll();
    }

    @Test
    public void testCounterIncrement() {
        Field counterField = ReflectionUtils.findField(idService.getClass(), "counter");
        assert counterField != null;
        ReflectionUtils.makeAccessible(counterField);
        ReflectionUtils.setField(counterField, idService, 0);

        long counter = idService.getId();
        Assert.assertEquals(0, counter);

        long counter2 = idService.getId();
        Assert.assertEquals(1, counter2);
    }

    @Test
    public void testCounterIncrementUpperLimit() {
        idService.getNewRange();
        long upperRangeLimit = rangeSize - 1;
        Field counterField = ReflectionUtils.findField(idService.getClass(), "counter");
        assert counterField != null;
        ReflectionUtils.makeAccessible(counterField);
        ReflectionUtils.setField(counterField, idService, upperRangeLimit);

        long counter = idService.getId();
        Assert.assertEquals(upperRangeLimit + 1, counter);
    }
}
