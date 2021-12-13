package ru.javawebinar.basejava.model;

import java.util.ArrayList;

public class BulletedListSection extends ResumeSectionList<String> {

    public BulletedListSection() {
        super(new ArrayList<>());
    }
}
