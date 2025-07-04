package com.jobseeker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.data.mongodb.uri=mongodb://localhost:27017/jobseeker_company_test"
})
class JobSeekerCompanyApplicationTests {

    @Test
    void contextLoads() {
        // This test ensures that the Spring context loads successfully
    }

}