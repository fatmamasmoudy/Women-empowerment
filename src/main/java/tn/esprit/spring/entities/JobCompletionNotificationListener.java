package tn.esprit.spring.entities;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			List<CV> results = jdbcTemplate.query("SELECT profile_title, about_me,center_of_interest,driving_lesence,internet_site,linkd_in,profile_description FROM cv", new RowMapper<CV>() {
				@Override
				public CV mapRow(ResultSet rs, int row) throws SQLException {
					return new CV(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5) , rs.getString(6), rs.getString(7));
				}
			});

			for (CV cv : results) {
				log.info("Found <" + cv + "> in the database.");
			}

		}
	}
}
