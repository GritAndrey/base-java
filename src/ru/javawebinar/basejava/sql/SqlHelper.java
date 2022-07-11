package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String query, SqlExecutor<T> sqlExecutor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return sqlExecutor.execute(ps);
        } catch (SQLException e) {
            // https://www.postgresql.org/docs/current/errcodes-appendix.html
            if ("23505".equals(e.getSQLState())) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }
}
