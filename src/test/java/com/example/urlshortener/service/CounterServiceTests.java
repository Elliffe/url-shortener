package com.example.urlshortener.service;

import org.junit.Assert;
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
public class CounterServiceTests {
    @Value("${rangeSize}")
    private int rangeSize;

    @Autowired
    private CounterService counterService;

    @Test
    public void testCounterIncrement() {
        Field counterField = ReflectionUtils.findField(counterService.getClass(), "counter");
        assert counterField != null;
        ReflectionUtils.makeAccessible(counterField);
        ReflectionUtils.setField(counterField, counterService, 0);

        long counter = counterService.getCounter();
        Assert.assertEquals(0, counter);

        long counter2 = counterService.getCounter();
        Assert.assertEquals(1, counter2);
    }

    @Test
    public void testCounterIncrementUpperLimit() {
        long upperRangeLimit = rangeSize - 1;
        Field counterField = ReflectionUtils.findField(counterService.getClass(), "counter");
        assert counterField != null;
        ReflectionUtils.makeAccessible(counterField);
        ReflectionUtils.setField(counterField, counterService, upperRangeLimit);

        long counter = counterService.getCounter();
        Assert.assertEquals(upperRangeLimit + 1, counter);
    }
}
