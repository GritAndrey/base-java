package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;
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
        sqlHelper.execute("DELETE FROM resume WHERE TRUE");
    }

    @Override
    public void update(Resume r) {
        log.info("Update: " + r);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(conn, r);
            insertContacts(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        log.info("Save: " + r);
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    insertContacts(conn, r);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        log.info("get resume with uuid: " + uuid);
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, r);
                    } while (rs.next());
                    return r;
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
        return sqlHelper.execute("" +
                " SELECT * FROM resume r " +
                " LEFT JOIN contact c ON r.uuid = c.resume_uuid" +
                " ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> map = new LinkedHashMap<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = Optional.ofNullable(map.get(uuid))
                        .orElse(new Resume(uuid, rs.getString("full_name")));
                map.put(uuid, resume);
                addContact(rs, resume);
            }
            return new ArrayList<>(map.values());
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

    private void deleteContacts(Connection conn, Resume r) {
        sqlHelper.execute("DELETE  FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        final String type = rs.getString("type");
        final Optional<String> mayBeValue = Optional.ofNullable(rs.getString("value"));
        mayBeValue.ifPresent(value -> r.addContact(ContactType.valueOf(type), value));
    }
}