package com.pluralsight.batchprocessing;

import javax.sql.DataSource;

import com.pluralsight.batchprocessing.model.InputPerson;
import com.pluralsight.batchprocessing.model.OutputPerson;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {

	@Bean
	public Job importUserJob(
			JobRepository jobRepository,
			PostCheckListener listener,
			Step preCheckStep,
			Step processRecordsStep
	) {
		return new JobBuilder("importUserJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(preCheckStep)
				.next(processRecordsStep)
				.listener(listener)
				.build();
	}

	@Bean
	public Step preCheckStep(
			JobRepository jobRepository,
			PlatformTransactionManager transactionManager,
			PreCheckTasklet tasklet
	) {
		return new StepBuilder("preCheck", jobRepository)
				.tasklet(tasklet, transactionManager)
				.build();
	}

	@Bean
	public Step processRecordsStep(
			JobRepository jobRepository,
			PlatformTransactionManager transactionManager,
			FlatFileItemReader<InputPerson> reader,
			PersonItemProcessor processor,
			JdbcBatchItemWriter<OutputPerson> writer
	) {
		reader.setLinesToSkip(1);
		return new StepBuilder("processRecords", jobRepository)
				.<InputPerson, OutputPerson> chunk(10, transactionManager)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

	@Bean
	public FlatFileItemReader<InputPerson> reader() {
		return new FlatFileItemReaderBuilder<InputPerson>()
			.name("personItemReader")
			.resource(new ClassPathResource("sample-data.csv"))
			.delimited()
			.names("firstName", "lastName", "age")
			.fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
				setTargetType(InputPerson.class);
			}})
			.build();
	}

	@Bean
	public JdbcBatchItemWriter<OutputPerson> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<OutputPerson>()
			.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
			.sql("INSERT INTO people (full_name, age) VALUES (:fullName, :age)")
			.dataSource(dataSource)
			.build();
	}
}
