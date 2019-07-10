package com.example.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.util.Date;

/**
 * 使用quartz进行定时调度
 */
public class HelloJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello World! - " + new Date());

        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        System.out.println("任务名称："+jobKey.getName());
    }
}
