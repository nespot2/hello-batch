package com.nespot2.hellobatch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2020/11/25
 **/
@Slf4j
public class JobLoggerListenerWithAnnotation {

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        log.info("before Job {}", jobExecution.getJobInstance().getJobName());
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        log.info("afterJob {}, {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
    }
}
