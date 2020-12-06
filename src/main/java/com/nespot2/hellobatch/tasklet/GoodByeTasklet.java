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
public class GoodByeTasklet implements Tasklet {

    @Value("#{jobParameters['name']}")
    private String name;

    private static final String GOOD_BYE = "Goodbye, {}";

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        ExecutionContext jobExecutionContext =
                chunkContext.getStepContext()
                        .getStepExecution()
                        .getExecutionContext();

        jobExecutionContext.put("haha", name);

        log.info(GOOD_BYE, name);

        return RepeatStatus.FINISHED;
    }
}
