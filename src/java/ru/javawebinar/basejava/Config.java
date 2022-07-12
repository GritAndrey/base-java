package ru.javawebinar.basejava;

import ru.javawebinar.basejava.storage.SqlStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String PROPS = ("resumes.properties");
    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final SqlStorage sqlStorage;

    private Config() {
        try (InputStream is = Config.class.getClassLoader().getResourceAsStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            sqlStorage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public SqlStorage getSqlStorage() {

        return sqlStorage;
    }
}