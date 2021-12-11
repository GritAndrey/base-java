package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ListStringSectionImpl implements Section<String> {
    private final List<String> content = new ArrayList<>();

    @Override
    public Stream<String> getContent() {
        return content.stream();
    }

    public void addContentElem(String elem) {
        content.add(elem);
    }
}
