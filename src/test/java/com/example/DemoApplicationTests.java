package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private JobRegistry jobRegistry;

    @Autowired
    private JobLauncher jobLauncher;

    /*@Resource(name = "helloWorldJob")
    private Job myjob;*/

    /*@Resource(name = "jobDemoJob")
    private Job job1;*/

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void test2(){
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);
    }

    /*@Test
    public void contextLoads() throws Exception {
        //JobParameters jobParameters = new JobParameters();
        try {
            JobExecution jobExecution = jobLauncher.run(myjob,new JobParametersBuilder()
                    .addDate("date",new Date()).toJobParameters() );
            System.out.println(jobExecution.getStatus());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }*/
    /*@Test
    public void test1() throws Exception {
        JobExecution jobExecution = jobLauncher.run(job1,new JobParametersBuilder()
                .addDate("date",new Date()).toJobParameters() );
    }*/

}
