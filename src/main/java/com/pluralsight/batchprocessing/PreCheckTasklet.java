package com.pluralsight.batchprocessing;

import com.pluralsight.batchprocessing.model.OutputPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PreCheckTasklet implements Tasklet {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreCheckTasklet.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PreCheckTasklet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RepeatStatus execute(@NonNull StepContribution contribution, @NonNull ChunkContext chunkContext) {
        LOGGER.info("Pre-Check: Verifying the DB is empty");

        int size = jdbcTemplate.query("SELECT full_name, age FROM people",
                (rs, row) -> new OutputPerson(rs.getString(1), rs.getInt(2))
        ).size();

        if (size != 0) {
            throw new IllegalStateException("DB is not empty!");
        }

        return RepeatStatus.FINISHED;
    }
}
