package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WorkPointsListSectionImpl extends ResumeSectionList<WorkPoint> {

    public WorkPointsListSectionImpl() {
        super(new ArrayList<>());
    }
}
