package com.nespot2.hellobatch.config;

import com.nespot2.hellobatch.validator.ParameterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2020/11/14
 * Using a RunIdIncrementer
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.name", havingValue = "hello_world_job5")
public class HelloWorldJob5 {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public CompositeJobParametersValidator validator() {
        CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
        DefaultJobParametersValidator defaultJobParametersValidator = new DefaultJobParametersValidator(new String[]{"fileName"}, new String[]{"run.id", "name", "orderDate"});
        defaultJobParametersValidator.afterPropertiesSet();
        validator.setValidators(Arrays.asList(new ParameterValidator(), defaultJobParametersValidator));
        return validator;

    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory
                .get("basicJob")
                .start(step1())
                .validator(validator())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory
                .get("step1")
                .tasklet(helloWorldTasklet(null, null))
                .build();
    }

    @Bean
    @StepScope
    public Tasklet helloWorldTasklet(
            @Value("#{jobParameters[fileName]}") String fileName,
            @Value("#{jobParameters[name]}") String name
    ) {
        return (contribution, chunkContext) -> {
            log.info("Hello, {}!", name);
            log.info("fileName={}!", fileName);
            return RepeatStatus.FINISHED;
        };
    }

}
