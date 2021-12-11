package ru.javawebinar.basejava.model;

public class WorkPoint {
    private String orgName;
    private String orgSite;
    private String workDescription;

    public WorkPoint(String orgName, String orgSite, String workDescription) {
        this.orgName = orgName;
        this.orgSite = orgSite;
        this.workDescription = workDescription;
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
