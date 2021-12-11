package ru.javawebinar.basejava.model;

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
}
