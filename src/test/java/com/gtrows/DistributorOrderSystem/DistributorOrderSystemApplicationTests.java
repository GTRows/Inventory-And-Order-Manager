package com.gtrows.DistributorOrderSystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@TestPropertySource(locations = "classpath:test-application.properties")
class DistributorOrderSystemApplicationTests {

    @Test
    void contextLoads() {
    }

}
