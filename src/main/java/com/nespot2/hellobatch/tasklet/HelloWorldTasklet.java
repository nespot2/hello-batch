package com.nespot2.hellobatch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2020/12/06
 **/
@Slf4j
public class HelloWorldTasklet implements Tasklet {

    @Value("#{jobParameters['name']}")
    private String name;

    private static final String HELLO_WORLD = "Hello, {}";


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        ExecutionContext jobExecutionContext =
                chunkContext.getStepContext()
                        .getStepExecution()
//						.getJobExecution()
                        .getExecutionContext();

        jobExecutionContext.put("name", name);

       log.info(HELLO_WORLD, name);

        return RepeatStatus.FINISHED;
    }
}
