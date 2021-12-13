package ru.javawebinar.basejava.model;

import java.util.Objects;

public class WorkPoint {
    private String orgName;
    private String orgSite;
    private String workDescription;

    public WorkPoint(String orgName, String orgSite, String workDescription) {
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
        WorkPoint workPoint = (WorkPoint) o;
        return Objects.equals(orgName, workPoint.orgName) && Objects.equals(orgSite, workPoint.orgSite) && Objects.equals(workDescription, workPoint.workDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgName, orgSite, workDescription);
    }

    @Override
    public String toString() {
        return "WorkPoint{" +
                "orgName='" + orgName + '\'' +
                ", orgSite='" + orgSite + '\'' +
                ", workDescription='" + workDescription + '\'' +
                '}';
    }
}
