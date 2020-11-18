package com.nespot2.hellobatch.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2020/11/19
 **/
@SpringBatchTest
@SpringBootTest
@TestPropertySource(properties = {"job.name=hello_world_job5", "spring.batch.job.enabled=false"})
class HelloWorldJob5Test {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void exceptionTest() throws Exception {
        final OffsetDateTime now = OffsetDateTime.now();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("orderDate", now.toString())
                .addString("fileName", "hello.csv", false) //non-identifying job parameters
                .addString("name", "lee", false) //non-identifying job parameters
                .toJobParameters();

        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
}