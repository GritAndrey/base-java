package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Experience {

    private final Link homePage;

    private final List<WorkPosition> workPositions = new ArrayList<>();

    public Experience(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name, url);
        workPositions.add(new WorkPosition(startDate, endDate, title, description));
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<WorkPosition> getWorkPositions() {
        return workPositions;
    }

    public void addWorkPosition(LocalDate startDate, LocalDate endDate, String title, String description) {
        workPositions.add(new WorkPosition(startDate, endDate, title, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return homePage.equals(that.homePage) && workPositions.equals(that.workPositions);
    }

    @Override
    public String toString() {
        return homePage + " " + workPositions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, workPositions);
    }

    private class WorkPosition {
        private LocalDate startDate;
        private LocalDate endDate;
        private String description;
        private String title;

        public WorkPosition(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WorkPosition that = (WorkPosition) o;
            return startDate.equals(that.startDate) && endDate.equals(that.endDate) && Objects.equals(description, that.description) && title.equals(that.title);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, description, title);
        }

        @Override
        public String toString() {
            return "Начало: " + startDate +
                            ", Окончание: " + endDate +
                            (description == null ? "" : ", Обязанности: " + description) +
                            ", Позиция: '" + title + '\'';
        }
    }
}
