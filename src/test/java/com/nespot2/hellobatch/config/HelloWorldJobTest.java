package com.nespot2.hellobatch.config;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2020/11/12
 **/
@SpringBatchTest
@SpringBootTest
@TestPropertySource(properties = "job.name=hello_world_job")
class HelloWorldJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void test() throws Exception {
        final OffsetDateTime now = OffsetDateTime.now();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("orderDate", now.toString())
                .toJobParameters();




        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        //같은 JobParameters로 job을 실행하면 JobInstanceAlreadyCompleteException 발생
        assertThrows(JobInstanceAlreadyCompleteException.class, () -> jobLauncherTestUtils.launchJob(jobParameters));
    }
}