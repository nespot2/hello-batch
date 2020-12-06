package com.nespot2.hellobatch.config;

import com.nespot2.hellobatch.incrementer.DailyJobTimestamper;
import com.nespot2.hellobatch.listener.JobLoggerListenerWithAnnotation;
import com.nespot2.hellobatch.tasklet.HelloWorldTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.listener.JobListenerFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2020/12/05
 * Adding a name to the Job's ExecutionContext
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.name", havingValue = "hello_world_job9")
public class HelloWorldJob9 {
    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public CompositeJobParametersValidator validator() {
        CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
        DefaultJobParametersValidator defaultJobParametersValidator = new DefaultJobParametersValidator(new String[]{"name"}, new String[]{"currentDate", "orderDate"});
        defaultJobParametersValidator.afterPropertiesSet();
        validator.setValidators(Arrays.asList(defaultJobParametersValidator));
        return validator;
    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory
                .get("basic")
                .start(step1())
                .validator(validator())
                .incrementer(new DailyJobTimestamper())
                .listener(
                        JobListenerFactoryBean.getListener(new JobLoggerListenerWithAnnotation())
                )
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory
                .get("step1")
                .tasklet(helloWorldTasklet())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet helloWorldTasklet() {
        return new HelloWorldTasklet();
    }


}
