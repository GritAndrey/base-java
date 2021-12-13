package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Experience {
    private String orgName;
    private String orgSite;
    private String workDescription;

    public Experience(String orgName, String orgSite, String workDescription) {
        this.orgName = orgName;
        this.orgSite = orgSite;
        this.workDescription = workDescription;
    }
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgSite() {
        return orgSite;
    }

    public void setOrgSite(String orgSite) {
        this.orgSite = orgSite;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience experience = (Experience) o;
        return Objects.equals(orgName, experience.orgName) && Objects.equals(orgSite, experience.orgSite) && Objects.equals(workDescription, experience.workDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgName, orgSite, workDescription);
    }

    @Override
    public String toString() {
        return "Experience{" +
                "orgName='" + orgName + '\'' +
                ", orgSite='" + orgSite + '\'' +
                ", workDescription='" + workDescription + '\'' +
                '}';
    }
}
