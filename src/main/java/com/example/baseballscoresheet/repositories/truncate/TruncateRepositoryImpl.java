package com.example.baseballscoresheet.repositories.truncate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TruncateRepositoryImpl implements TruncateRepository {
    private final JdbcTemplate jdbcTemplate;

    public TruncateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Truncates the specified table.
     *
     * @param tableName the name of the table to be truncated
     */
    @Override
    public void truncateTable(String tableName) {
        jdbcTemplate.execute("TRUNCATE TABLE " + tableName + " RESTART IDENTITY CASCADE");
    }

    /**
     * Resets the auto-increment counter for the specified table.
     *
     * @param tableName the name of the table for which to reset the auto-increment counter
     */
    @Override
    public void resetAutoIncrement(String tableName) {
        // Get the sequence name for the specified table
        String sequenceNameQuery = "SELECT pg_get_serial_sequence('" + tableName + "', 'id')";
        String sequenceName = jdbcTemplate.queryForObject(sequenceNameQuery, String.class);

        if (sequenceName != null) {
            jdbcTemplate.execute("ALTER SEQUENCE " + sequenceName + " RESTART WITH 1");
        } else {
            throw new IllegalArgumentException("No sequence found for table " + tableName);
        }
    }
}