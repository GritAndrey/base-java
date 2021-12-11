package ru.javawebinar.basejava.model;

import java.util.stream.Stream;

public interface Section<T> {
     Stream<T> getContent();
}
