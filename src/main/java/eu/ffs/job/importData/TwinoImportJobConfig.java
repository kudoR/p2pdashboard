package eu.ffs.job.importData;

import eu.ffs.Constants;
import eu.ffs.repository.TwinoAccountEntryRepository;
import eu.ffs.repository.entity.TwinoAccountEntry;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static eu.ffs.Constants.JOB_NAME_TWINO;

@Configuration
@EnableBatchProcessing
public class TwinoImportJobConfig implements AbstractImportJobConfig {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private TwinoAccountEntryRepository repository;

    @Bean
    ItemWriter<TwinoAccountEntry> twinoItemWriter() {
        return list -> repository.save(list);
    }

    @StepScope
    @Bean
    ItemReader<TwinoAccountEntry> twinoItemReader(@Value("#{jobParameters['inputPath']}") String inputPath) {

        return new AccountEntryItemReaderFactory()
                .withInputPath(inputPath)
                .withTargetType(TwinoAccountEntry.class)
                .build();
    }


    @Bean
    Step twinoJobStep() {
        return stepBuilderFactory.get(Constants.STEP_NAME)
                .<TwinoAccountEntry, TwinoAccountEntry>chunk(10)
                .reader(twinoItemReader(null))
                .writer(twinoItemWriter())
                .build();
    }

    @Bean
    protected Job twinoImportJob() {
        return jobBuilderFactory.get(Constants.JOB_NAME_TWINO)
                .incrementer(new RunIdIncrementer())
                .flow(twinoJobStep())
                .end()
                .build();
    }

    public JobExecution perform(String inputPath) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters parameters = new JobParametersBuilder()
                .addString(
                        JOB_NAME_TWINO,
                        String.valueOf(System.currentTimeMillis())
                )
                .addString(
                        "inputPath",
                        inputPath
                )
                .toJobParameters();

        JobExecution execution = jobLauncher.run(twinoImportJob(), parameters);
        System.out.println("Job finished with status :" + execution.getStatus());
        return execution;
    }

}
