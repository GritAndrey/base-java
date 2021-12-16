package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.NOW;

public class Organization {

    private final Link homePage;

    private final List<Position> positions = new ArrayList<>() {
        @Override
        public String toString() {
            Iterator<Position> it = iterator();
            if (!it.hasNext())
                return "";
            StringBuilder sb = new StringBuilder();
            for (; ; ) {
                Position e = it.next();
                sb.append(e);
                if (!it.hasNext())
                    return sb.toString();
                sb.append(';').append(' ');
            }
        }
    };

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name, url);
        positions.add(new Position(startDate, endDate, title, description));
    }

    public void addWorkPosition(LocalDate startDate, LocalDate endDate, String title, String description) {
        positions.add(new Position(startDate, endDate, title, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return homePage.equals(that.homePage) && positions.equals(that.positions);
    }

    @Override
    public String toString() {
        return homePage + " " + positions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }

    public static class Position {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String description;
        private final String title;

        public Position(int startYear, Month startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), NOW, title, description);
        }

        public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
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
            Position that = (Position) o;
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
