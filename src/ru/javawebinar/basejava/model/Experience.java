package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Experience {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;
    private final Link homePage;
    private final String title;


    public Experience(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.homePage = new Link(name, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return homePage.equals(that.homePage)
                && startDate.equals(that.startDate)
                && endDate.equals(that.endDate)
                && title.equals(that.title)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        return "Experience{" +
                "homePage=" + homePage +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
