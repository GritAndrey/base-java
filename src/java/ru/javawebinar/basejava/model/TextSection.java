package ru.javawebinar.basejava.model;

import java.io.Serial;
import java.util.Objects;
import java.util.stream.Stream;

public class TextSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;

    private String content;

    public TextSection() {
    }

    public TextSection(String content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Stream<String> getContent() {
        return Stream.of(content);
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
