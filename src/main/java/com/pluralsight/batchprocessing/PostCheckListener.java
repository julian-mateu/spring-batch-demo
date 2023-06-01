package com.pluralsight.batchprocessing;

import com.pluralsight.batchprocessing.model.OutputPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostCheckListener implements JobExecutionListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostCheckListener.class);
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PostCheckListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOGGER.info("Post-Check: Verifying the DB contains the data");

			List<OutputPerson> people = jdbcTemplate.query(
					"SELECT full_name, age FROM people",
					(rs, row) -> new OutputPerson(rs.getString(1), rs.getInt(2))
			);

			people.forEach(person -> LOGGER.info("Found <{{}}> in the database.", person));

			if (people.size() != 3) {
				throw new IllegalStateException("DB is missing records!");
			}
		}
	}
}
