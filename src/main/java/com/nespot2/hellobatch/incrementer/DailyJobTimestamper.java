package com.nespot2.hellobatch.incrementer;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.util.Date;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2020/11/19
 **/
public class DailyJobTimestamper implements JobParametersIncrementer {

    @Override
    public JobParameters getNext(JobParameters parameters) {
        return new JobParametersBuilder(parameters)
                .addDate("currentDate", new Date())
                .toJobParameters();
    }
}