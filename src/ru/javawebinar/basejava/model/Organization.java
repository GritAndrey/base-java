package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"homePage", "positions"})
public class Organization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "position")
    private final List<Position> positions = new ArrayList<>();
    private Link homePage;

    public Organization() {
    }

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name, url);
        positions.add(new Position(startDate, endDate, title, description));
    }

    public Organization(Link homepage, List<Position> positions) {
        this.positions.addAll(positions);
        this.homePage = homepage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public void addWorkPosition(LocalDate startDate, LocalDate endDate, String title, String description) {
        positions.add(new Position(startDate, endDate, title, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) && Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }

    @Override
    public String toString() {
        return homePage + " " + positions;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
        private LocalDate endDate;
        private String description;
        private String title;

        public Position() {
        }

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

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return startDate.equals(position.startDate)
                    && endDate.equals(position.endDate)
                    && Objects.equals(description, position.description)
                    && title.equals(position.title);
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
