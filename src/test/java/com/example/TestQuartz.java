package com.example;

import com.example.job.DumbJob;
import com.example.job.HelloJob;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;

public class TestQuartz {


    private void run() throws Exception {
        System.out.println("------- 初始化 ----------------------");

        // 首先要实例化scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        System.out.println("------- 初始化完成 -----------");

        // 获取给定时间的下一个完整分钟的时间，例如给定时间 08:13:54 则会反回 08:14:00
        Date runTime = DateBuilder.evenMinuteDate(new Date());

        System.out.println("------- Job安排 -------------------");

        // 获取job实例
        JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();
        // 在下一轮分钟触发运行
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();
        // 告诉quartz使用某个trigger执行某个job
        scheduler.scheduleJob(job, trigger);
        System.out.println(job.getKey() + " 将会运行于: " + runTime);

        // 启动scheduler
        scheduler.start();

        System.out.println("------- 开始安排 -----------------");

        System.out.println("------- 等待65秒 -------------");
        Thread.sleep(65L );

        // 关闭scheduler
        System.out.println("------- 关闭 ---------------------");
        scheduler.shutdown(true);
        System.out.println("------- 关闭完成 -----------------");

    }

    @Test
    public void test1() throws Exception {
        run();
    }

    @Test
    public void test2() throws Exception {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail job = newJob(DumbJob.class)
                .withIdentity("myJob", "group2") // name "myJob", group "group1"
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .build();
        // 获取给定时间的下一个完整分钟的时间，例如给定时间 08:13:54 则会反回 08:14:00
        Date runTime = DateBuilder.evenMinuteDate(new Date());
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2").startAt(runTime).build();

        scheduler.scheduleJob(job,trigger);
        System.out.println("------- 开始安排 -----------------");
        System.out.println(job.getKey() + " 将会运行于: " + runTime);
        scheduler.start();
        System.out.println("------- 关闭 ---------------------");
        scheduler.shutdown(true);
        System.out.println("------- 关闭完成 -----------------");
    }


}
