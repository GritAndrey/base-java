package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ListStringSectionImpl implements Section<String> {
    private final List<String> elements = new ArrayList<>();

    public List<String> getElements() {
        return elements;
    }

    @Override
    public Stream<String> getContent() {
        return elements.stream();
    }

}
