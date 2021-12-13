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

    public void deleteContentElem(int index) {
        content.remove(index);
    }

    public String getContentElem(int index) {
        return content.get(index);
    }

    public void updateContentElem(String element, int index) {
        content.set(index, element);
    }
}
