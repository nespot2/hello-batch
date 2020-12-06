package com.nespot2.hellobatch.config;

import com.nespot2.hellobatch.incrementer.DailyJobTimestamper;
import com.nespot2.hellobatch.listener.JobLoggerListenerWithAnnotation;
import com.nespot2.hellobatch.tasklet.GoodByeTasklet;
import com.nespot2.hellobatch.tasklet.HelloWorldTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.listener.JobListenerFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2020/12/06
 * ExecutionContextPromotionListener 사용
 **/
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "job.name", havingValue = "hello_world_job10")
public class HelloWorldJob10 {

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
                .next(step2())
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
                .listener(promotionListener())
                .build();
    }


    @Bean
    public Step step2() {
        return this.stepBuilderFactory
                .get("step2")
                .tasklet(goodByeTasklet())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet helloWorldTasklet() {
        return new HelloWorldTasklet();
    }

    @Bean
    @StepScope
    public Tasklet goodByeTasklet() {
        return new GoodByeTasklet();
    }

    @Bean
    public StepExecutionListener promotionListener() {
        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
        listener.setKeys(new String[]{"name"});
        return listener;

    }
}
