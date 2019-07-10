package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 嵌套调用job
 */
/*@Configuration*/
public class NestedDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job childJobOne;

    @Autowired
    private Job childJobTwo;

    @Bean
    public Job parentJob(JobRepository Repository, PlatformTransactionManager transactionManager){
        return jobBuilderFactory.get("parentJob")
                .start(childJob1(Repository,transactionManager))
                .next(childJob2(Repository,transactionManager))
                .build();
    }

    /**
     * 将Job转化为step
     * @param Repository
     * @param transactionManager
     * @return
     */
    //特殊的Step
    public Step childJob1(JobRepository Repository, PlatformTransactionManager transactionManager){
        return new JobStepBuilder(new StepBuilder("childJob1"))
                .job(childJobOne)
                .launcher(jobLauncher)
                .repository(Repository)
                .transactionManager(transactionManager)
                .build();
    }

    public Step childJob2(JobRepository Repository, PlatformTransactionManager transactionManager){
        return new JobStepBuilder(new StepBuilder("childJob2"))
                .job(childJobTwo)
                .launcher(jobLauncher)
                .repository(Repository)
                .transactionManager(transactionManager)
                .build();
    }
}
