package com.example.reader;

import com.example.entity.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 从多个文件中读取数据
 */
/*@Configuration*/
public class MultiFileItemReaderDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("classpath:/student*.txt")
    private Resource[] fileResources;

    @Autowired
    @Qualifier("multiFlatItemWriter")
    private ItemWriter<? super User> multiFlatItemWriter;

    @Bean
    public Job multiFileItemReader(){
        return jobBuilderFactory.get("multiFileItemReader")
                .start(multiFlileReaderStep())
                .build();
    }

    @Bean
    public Step multiFlileReaderStep(){
        return stepBuilderFactory.get("multiFlileReaderStep")
                .<User,User>chunk(10)
                .reader(multiReader())
                .writer(multiFlatItemWriter)
                .build();
    }



    @Bean
    @StepScope
    public MultiResourceItemReader<? extends User> multiReader() {
        MultiResourceItemReader<User> resourceItemReader = new MultiResourceItemReader<>();
        resourceItemReader.setResources(fileResources);
        resourceItemReader.setDelegate(itemReader());
        return resourceItemReader;
    }

    @Bean
    public FlatFileItemReader<User> itemReader(){
        FlatFileItemReader<User> reader = new FlatFileItemReader<>();
        //reader.setLinesToSkip(1);
        DefaultLineMapper<User> lineMapper = new DefaultLineMapper<User>(){
            {
                setLineTokenizer(new DelimitedLineTokenizer(){
                    {
                        setNames(new String[]{"id","username","password","age"});
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<User>(){
                    {
                        setTargetType(User.class);
                    }
                });
            }
        };
        reader.setLineMapper(lineMapper);
        return reader;
    }
}
