package com.nespot2.hellobatch.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2020/11/14
 **/
@SpringBatchTest
@SpringBootTest
@TestPropertySource(properties = {"job.name=hello_world_job4", "spring.batch.job.enabled=false"})
class HelloWorldJob4Test {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void exceptionTest() throws Exception {
        final OffsetDateTime now = OffsetDateTime.now();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("orderDate", now.toString())
                .addString("name", "lee", false) //non-identifying job parameters
                .toJobParameters();


        Assertions.assertThrows(JobParametersInvalidException.class, () -> jobLauncherTestUtils.launchJob(jobParameters));

    }
}