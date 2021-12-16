package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class OrganizationSection extends Section {
    private final List<Organization> organizations;

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

    @SuppressWarnings("unchecked")
    @Override
    public Stream<Organization> getContent() {
        return organizations.stream();
    }
}
