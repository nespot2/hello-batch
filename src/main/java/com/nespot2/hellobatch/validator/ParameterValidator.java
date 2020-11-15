package com.nespot2.hellobatch.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

/**
 * @author nespot2
 * @version 0.0.1
 * @since 2020/11/14
 **/
public class ParameterValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        final String fileName = parameters.getString("fileName");

        if(!StringUtils.hasText(fileName)){
            throw new JobParametersInvalidException("fileName 파라메터가 없습니다.");
        }else if(!StringUtils.endsWithIgnoreCase(fileName,"csv")){
            throw new JobParametersInvalidException("fileName 파라메터가 csv 확장자를 사용하지 않습니다");
        }
    }
}
