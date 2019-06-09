package com.example.urlshortener.Service;

import com.example.urlshortener.service.MongoCounterService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoCounterServiceTests {

    @Autowired
    private MongoCounterService mongoCounterService;

    @Test
    public void testCounterIncrement() {
        long counter = mongoCounterService.getCounter();
        Assert.assertEquals(0, counter);

        long counter2 = mongoCounterService.getCounter();
        Assert.assertEquals(1, counter2);
    }

    @Test
    public void testCounterIncrementUpperLimit() {
        long upperRangeLimit = 999;
        Field counterField = ReflectionUtils.findField(mongoCounterService.getClass(), "counter");
        assert counterField != null;
        ReflectionUtils.makeAccessible(counterField);
        ReflectionUtils.setField(counterField, mongoCounterService, upperRangeLimit);

        long counter = mongoCounterService.getCounter();
        Assert.assertEquals(upperRangeLimit + 1, counter);
    }
}
