package com.markuvinicius.kafka.streams;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.Assert.assertEquals;

public class TestContainers {
    @Rule
    public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine"))
            .withExposedPorts(6379);

    //private RedisBackedCache underTest;

    @Before
    public void setUp() {
        // Assume that we have Redis running locally?
       //underTest = new RedisBackedCache("localhost", 6379);
    }

    @Test
    public void testSimplePutAndGet() {
        //underTest.put("test", "example");

        //String retrieved = underTest.get("test");
        //assertEquals("example", retrieved);
    }
}
