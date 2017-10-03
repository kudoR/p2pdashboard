package eu.ffs.job.importData;

import eu.ffs.Constants;
import eu.ffs.repository.ViventorAccountEntryRepository;
import eu.ffs.repository.entity.ViventorAccountEntry;
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
import org.springframework.batch.item.excel.poi.ViventorAccountEntryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static eu.ffs.Constants.JOB_NAME_VIVENTOR;

@Configuration
@EnableBatchProcessing
public class ViventorImportJobConfig implements AbstractImportJobConfig {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ViventorAccountEntryRepository repository;

    @Bean
    ItemWriter<ViventorAccountEntry> viventorItemWriter() {
        return list -> repository.save(list);
    }

    @StepScope
    @Bean
    ItemReader<ViventorAccountEntry> viventorItemReader(@Value("#{jobParameters['inputPath']}") String inputPath) {

        return new AccountEntryItemReaderFactory()
                .withAccountEntryItemReader(new ViventorAccountEntryItemReader())
                .withInputPath(inputPath)
                .withTargetType(ViventorAccountEntry.class)
                .build();
    }

    @Bean
    Step viventorJobStep() {
        return stepBuilderFactory.get(Constants.STEP_NAME)
                .<ViventorAccountEntry, ViventorAccountEntry>chunk(10)
                .reader(viventorItemReader(null))
                .writer(viventorItemWriter())
                .build();
    }

    @Bean
    protected Job viventorImportJob() {
        return jobBuilderFactory.get(Constants.JOB_NAME_VIVENTOR)
                .incrementer(new RunIdIncrementer())
                .flow(viventorJobStep())
                .end()
                .build();
    }

    public JobExecution perform(String inputPath) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters parameters = new JobParametersBuilder().addString(
                JOB_NAME_VIVENTOR,
                String.valueOf(System.currentTimeMillis())

        ).addString(
                "inputPath",
                inputPath
        ).toJobParameters();

        JobExecution execution = jobLauncher.run(viventorImportJob(), parameters);
        System.out.println("Job finished with status :" + execution.getStatus());
        return execution;
    }

}
