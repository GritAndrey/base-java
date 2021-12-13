package ru.javawebinar.basejava.model;

import java.util.Objects;
import java.util.stream.Stream;

public class StringSectionImpl implements Section<String> {
    private String content;

    public StringSectionImpl(String content) {
        this.content = content;
    }

    @Override
    public Stream<String> getContent() {
        return Stream.of(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringSectionImpl that = (StringSectionImpl) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    public void setContent(String content) {
        this.content = content;
    }
}
