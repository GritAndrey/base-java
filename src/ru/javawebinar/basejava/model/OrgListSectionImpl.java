package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OrgListSectionImpl implements Section<WorkPoint> {
    private final List<WorkPoint> content = new ArrayList<>();

    @Override
    public Stream<WorkPoint> getContent() {
        return content.stream();
    }

    public void addWorkPoint(WorkPoint workPoint) {
        content.add(workPoint);
    }
}
