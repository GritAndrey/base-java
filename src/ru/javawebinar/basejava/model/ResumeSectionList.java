package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class ResumeSectionList<T> implements Section<T> {
    protected final List<T> elements;

    public ResumeSectionList(List<T> elements) {
        Objects.requireNonNull(elements, "elements must not be null");
        this.elements = elements;
    }

    public List<T> getElements() {
        return elements;
    }

    @Override
    public Stream<T> getContent() {
        return elements.stream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResumeSectionList<?> that = (ResumeSectionList<?>) o;
        return elements.equals(that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

}
