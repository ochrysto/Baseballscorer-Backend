package com.example.baseballscoresheet.repositories.truncate;

public interface TruncateRepository {
    void truncateTable(String tableName);
    void resetAutoIncrement(String tableName);
}
