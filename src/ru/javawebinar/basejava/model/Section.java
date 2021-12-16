package ru.javawebinar.basejava.model;

import java.util.stream.Stream;

public abstract class Section {

    public abstract <T> Stream<T> getContent();
}
