package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.stream.Stream;

public abstract class ResumeSectionList<T> implements Section<T> {
    protected final List<T> elements;

    public ResumeSectionList(List<T> elements) {
        this.elements = elements;
    }

    public List<T> getElements() {
        return elements;
    }

    @Override
    public Stream<T> getContent() {
        return elements.stream();
    }
}
