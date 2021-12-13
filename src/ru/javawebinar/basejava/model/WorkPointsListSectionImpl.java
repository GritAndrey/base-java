package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WorkPointsListSectionImpl implements Section<WorkPoint> {
    private final List<WorkPoint> workPoints = new ArrayList<>();

    public List<WorkPoint> getWorkPoints() {
        return workPoints;
    }

    @Override
    public Stream<WorkPoint> getContent() {
        return workPoints.stream();
    }


}
