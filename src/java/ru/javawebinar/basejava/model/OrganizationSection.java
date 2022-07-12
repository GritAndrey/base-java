package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


public class OrganizationSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "organization")
    private List<Organization> organizations;

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));

    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "organizations=" + organizations +
                '}';
    }

    @SuppressWarnings("unchecked")
    @Override
    public Stream<Organization> getContent() {
        return organizations.stream();
    }
}
