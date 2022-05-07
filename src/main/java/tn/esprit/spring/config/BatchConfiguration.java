package tn.esprit.spring.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import tn.esprit.spring.entities.CV;
import tn.esprit.spring.entities.CVItemProcessor;
import tn.esprit.spring.entities.JobCompletionNotificationListener;
import tn.esprit.spring.services.CsvService;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;
    
	  
    @Bean
    public FlatFileItemReader<CV> reader() {
        FlatFileItemReader<CV> reader = new FlatFileItemReader<CV>();
        reader.setResource(new ClassPathResource("MyCV.csv"));
        reader.setLineMapper(new DefaultLineMapper<CV>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "profileTitle", "aboutMe","internetSite","drivingLesence","linkdIn","profileDescription","centerOfInterest"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<CV>() {{
                setTargetType(CV.class);
            }});
        }});
        return reader;
    }

   @Bean
    public CVItemProcessor processor() {
        return new CVItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<CV> writer() {
        JdbcBatchItemWriter<CV> writer = new JdbcBatchItemWriter<CV>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<CV>());
        writer.setSql("INSERT INTO cv (profile_title, about_me,center_of_interest,driving_lesence,internet_site,linkd_in,profile_description) " + "VALUES (:profile_title, :about_me,:center_of_interest,:driving_lesence ,:internet_site ,:linkd_in,:profile_description)");
        writer.setDataSource(dataSource);
        return writer;
    
       
    }

 // @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<CV, CV> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}