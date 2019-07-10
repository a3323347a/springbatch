package com.example.reader;

import com.example.entity.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.Writer;

/*@Configuration
@EnableBatchProcessing*/
public class FileItemReader {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /*@Autowired
    @Qualifier("flatItemWriter")
    private ItemWriter<? super User> flatItemWriter;*/

    @Bean
    public Job fileReaderJob(){
        return jobBuilderFactory.get("fileReaderJob")
                .start(fileItemReaderStep())
                .build();
    }

    @Bean
    public Step fileItemReaderStep(){
        return stepBuilderFactory.get("fileItemReaderStep")
                .<User,User>chunk(10)
                .reader(itemReader())
                .writer(flatItemWriterDemo())
                .build();

    }

    @Bean
    public FlatFileItemWriter<User> flatItemWriterDemo(){
        FlatFileItemWriter<User> flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setResource(new ClassPathResource("stu_test.csv"));
        flatFileItemWriter.setLineAggregator(new DelimitedLineAggregator<User>(){
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<User>(){
                    {
                        setNames(new String[]{"id","username","password","age"});
                    }
                });
            }
        });
        flatFileItemWriter.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write("##student## start...");
            }
        });
        flatFileItemWriter.setFooterCallback(new FlatFileFooterCallback() {
            @Override
            public void writeFooter(Writer writer) throws IOException {
                writer.write("##student## end...");
            }
        });
        return flatFileItemWriter;
    }

    @Bean
    public FlatFileItemReader<User> itemReader(){
        FlatFileItemReader<User> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("student.txt"));
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
