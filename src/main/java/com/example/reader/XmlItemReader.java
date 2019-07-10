package com.example.reader;

import com.example.entity.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

/*@Configuration*/
public class XmlItemReader {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("xmlItemWriter")
    private ItemWriter<? super User> xmlItemWriter;

    @Bean
    public Job xmlItemReaderJob()  {
        return jobBuilderFactory.get("xmlItemReaderJob")
                .start(xmlItemReaderStep())
                .build();
    }

    @Bean
    public Step xmlItemReaderStep()  {
        return stepBuilderFactory.get("xmlItemReaderStep")
                .<User,User>chunk(10)
                .reader(xmlItemReaderDemo())
                .writer(xmlItemWriter)
                .build();

    }

    @Bean
    @StepScope
    public StaxEventItemReader<User> xmlItemReaderDemo() {
        StaxEventItemReader<User> reader = new StaxEventItemReader<>();
        reader.setResource(new ClassPathResource("student.xml"));
        reader.setFragmentRootElementName("user");

        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        Map<String,Class> map = new HashMap<>();
        map.put("user",User.class);
        try {
            xStreamMarshaller.setAliases(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        reader.setUnmarshaller(xStreamMarshaller);
        return reader;
    }

}
