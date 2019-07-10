package com.example.job;

import org.quartz.*;

/**
 * @author zhengjiaxin
 * @date 2019/7/8
 */
public class DumbJob implements Job {

    public DumbJob(){

    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobKey key = context.getJobDetail().getKey();
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");
        System.out.println("Instance " + key + " of DumbJob says: "
                + jobSays + ", and val is: " + myFloatValue);
    }
}
