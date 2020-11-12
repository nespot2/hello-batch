package com.nespot2.hellobatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2020/11/12
 **/
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.name", havingValue = "hello_world_job")
public class HelloWorldJob {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("basicJob").start(step1()).build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {
            System.out.println("Hello, world!");
            return RepeatStatus.FINISHED;
        }).build();
    }


}
