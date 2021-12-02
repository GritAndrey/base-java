import java.util.stream.IntStream;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        IntStream.range(0, size).forEach(index -> storage[index] = null);
        size = 0;
    }

    void save(Resume r) {
        if (r == null) return;
        storage[size++] = r;
    }

    Resume get(String uuid) {
        if (uuid == null) return null;
        int index = findIndexByUuid(uuid);
        return index == -1 ? null : storage[index];
    }

    void delete(String uuid) {
        if (uuid == null) return;
        int index = findIndexByUuid(uuid);
        if (index != -1) {
            System.arraycopy(storage, 0, storage, 0, index);
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            size--;
        }
    }

    private int findIndexByUuid(String uuid) {
        return IntStream.range(0, size)
                .filter(i -> storage[i].uuid.equals(uuid))
                .findFirst().orElse(-1);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] actualSizeResumeArray = new Resume[size];
        System.arraycopy(storage, 0, actualSizeResumeArray, 0, size);
        return actualSizeResumeArray;
    }

    int size() {
        return this.size;
    }
}
