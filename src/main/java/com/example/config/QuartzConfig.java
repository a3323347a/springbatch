package com.example.config;

import com.example.job.PrintTimeJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhengjiaxin
 * @date 2019/7/8
 */
//@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail printTimeJobDetail(){
        return JobBuilder.newJob(PrintTimeJob.class)
                .withIdentity("job1","group1")
                .build();
    }

    @Bean
    public Trigger printTimeJobTrigger() {
        return null;
    }
}
