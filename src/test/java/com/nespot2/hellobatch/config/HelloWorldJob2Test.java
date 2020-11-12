package com.nespot2.hellobatch.config;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2020/11/13
 **/
@SpringBatchTest
@SpringBootTest
@TestPropertySource(properties = "job.name=hello_world_job2")
class HelloWorldJob2Test {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void test() throws Exception {
        final OffsetDateTime now = OffsetDateTime.now();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("orderDate", now.toString())
                .addString("name", "lee", false) //non-identifying job parameters
                .toJobParameters();


        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
}