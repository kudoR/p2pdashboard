package eu.ffs.job.importData;

import eu.ffs.Constants;
import eu.ffs.repository.MintosAccountEntryRepository;
import eu.ffs.repository.entity.MintosAccountEntry;
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

import static eu.ffs.Constants.JOB_NAME_MINTOS;

@Configuration
@EnableBatchProcessing
public class MintosImportJobConfig implements AbstractImportJobConfig {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MintosAccountEntryRepository repository;

    @Bean
    ItemWriter<MintosAccountEntry> mintosItemWriter() {
        return list -> repository.save(list);
    }

    @StepScope
    @Bean
    ItemReader<MintosAccountEntry> mintosItemReader(@Value("#{jobParameters['inputPath']}") String inputPath) {

        return new AccountEntryItemReaderFactory()
                .withInputPath(inputPath)
                .withTargetType(MintosAccountEntry.class)
                .build();
    }

    @Bean
    Step mintosJobStep() {
        return stepBuilderFactory.get(Constants.STEP_NAME)
                .<MintosAccountEntry, MintosAccountEntry>chunk(10)
                .reader(mintosItemReader(null))
                .writer(mintosItemWriter())
                .build();
    }


    @Bean
    protected Job mintosImportJob() {
        return jobBuilderFactory.get(Constants.JOB_NAME_MINTOS)
                .incrementer(new RunIdIncrementer())
                .flow(mintosJobStep())
                .end()
                .build();
    }


    public JobExecution perform(String inputPath) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters parameters = new JobParametersBuilder().addString(
                JOB_NAME_MINTOS,
                String.valueOf(System.currentTimeMillis())
        ).addString(
                "inputPath",
                inputPath
        ).toJobParameters();

        JobExecution execution = jobLauncher.run(mintosImportJob(), parameters);
        System.out.println("Job finished with status :" + execution.getStatus());
        return execution;

    }
}
