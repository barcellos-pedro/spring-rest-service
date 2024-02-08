package com.pedro.rest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Disabled
class RestApplicationTests {

    @Test
    void contextLoads() {
        RestApplication app = new RestApplication();
        assertNotNull(app);
    }
}
