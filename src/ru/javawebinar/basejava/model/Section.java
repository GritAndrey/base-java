package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.stream.Stream;

public abstract class Section implements Serializable {

    public abstract <T> Stream<T> getContent();
}
