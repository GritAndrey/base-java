package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger log = Logger.getLogger(AbstractStorage.class.getName());
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        log.info("Delete all");
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        log.info("Update: " + r);
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid=?", statement -> {
            statement.setString(1, r.getFullName());
            statement.setString(2, r.getUuid());
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        log.info("Save: " + r);
        sqlHelper.<Void>execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", statement -> {
            statement.setString(1, r.getUuid());
            statement.setString(2, r.getFullName());
            statement.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        log.info("get resume with uuid: " + uuid);
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?", statement -> {
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });

    }

    @Override
    public void delete(String uuid) {
        log.info("Delete resume with uuid: " + uuid);
        sqlHelper.<Void>execute("DELETE FROM resume r WHERE r.uuid =? ", statement -> {
            statement.setString(1, uuid);
            final int rows = statement.executeUpdate();
            if (rows == 0)
                throw new NotExistStorageException(uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        log.info("Get all resumes");
        return sqlHelper.execute("SELECT * FROM resume r ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        log.info("Get size");
        return sqlHelper.execute("SELECT COUNT(*) FROM resume r", statement -> {
            final ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });

    }
}