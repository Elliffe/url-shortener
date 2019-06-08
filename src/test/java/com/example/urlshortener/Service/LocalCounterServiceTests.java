package com.example.urlshortener.Service;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.LocalCounterService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalCounterServiceTests {

    @Autowired
    private LocalCounterService localCounterService;

    @Test
    public void testCounterIncrement() {
        long counter = localCounterService.getCounter();
        Assert.assertEquals(1, counter);

        long counter2 = localCounterService.getCounter();
        Assert.assertEquals(2, counter2);
    }

    @Test
    public void testCounterIncrementUpperLimit() {
        long upperLimit = 9223372036854775806L;
        Field counterField = ReflectionUtils.findField(localCounterService.getClass(), "counter");
        assert counterField != null;
        ReflectionUtils.makeAccessible(counterField);
        ReflectionUtils.setField(counterField, localCounterService, upperLimit);

        long counter = localCounterService.getCounter();
        Assert.assertEquals(upperLimit + 1, counter);
    }

}
