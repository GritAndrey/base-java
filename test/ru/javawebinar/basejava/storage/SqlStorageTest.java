package ru.javawebinar.basejava.storage;

import org.junit.Ignore;
import ru.javawebinar.basejava.Config;
@Ignore
public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.get().getSqlStorage());
    }
}

