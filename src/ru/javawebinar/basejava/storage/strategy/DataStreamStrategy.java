package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DataStreamStrategy implements SerializationStrategy {
    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            int contactsSize = dis.readInt();
            for (int con = 0; con < contactsSize; con++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String contactValue = dis.readUTF();
                resume.addContact(contactType, contactValue);
            }
            int sectionsSize = dis.readInt();
            resume.getSections().putAll(readSections(dis, sectionsSize));
            return resume;
        }
    }

    @Override
    public void write(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            writeSections(dos, sections);
        }
    }

    private void writeSections(DataOutputStream dos, Map<SectionType, Section> sections) throws IOException {
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            switch (entry.getKey()) {
                case PERSONAL, OBJECTIVE -> dos.writeUTF(entry.getValue().toString());

                case ACHIEVEMENT, QUALIFICATIONS -> {
                    final List<String> items = ((ListSection) entry.getValue()).getItems();
                    dos.writeInt(items.size());
                    for (String item : items) {
                        dos.writeUTF(item);
                    }
                }

                case EXPERIENCE, EDUCATION -> {
                    final List<Organization> organizations = ((OrganizationSection) entry.getValue()).getOrganizations();
                    dos.writeInt(organizations.size());
                    for (Organization organization : organizations) {
                        final Link homePage = organization.getHomePage();
                        dos.writeUTF(homePage.getName());
                        if (homePage.getUrl() != null) {
                            dos.writeUTF(homePage.getUrl());
                        } else {
                            dos.writeUTF("");
                        }
                        writePositions(dos, organization);
                    }
                }
            }
        }
    }

    private void writePositions(DataOutputStream dos, Organization organization) throws IOException {
        final List<Organization.Position> positions = organization.getPositions();
        dos.writeInt(positions.size());
        for (Organization.Position position : positions) {
            dos.writeInt(position.getStartDate().getYear());
            dos.writeUTF(position.getStartDate().getMonth().name());
            dos.writeInt(position.getEndDate().getYear());
            dos.writeUTF(position.getEndDate().getMonth().name());
            dos.writeUTF(position.getTitle());
            if (position.getDescription() != null) {
                dos.writeUTF(position.getDescription());
            } else {
                dos.writeUTF("");
            }
        }
    }

    private Map<SectionType, Section> readSections(DataInputStream dis, int sectionsSize) throws IOException {
        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
        for (int i = 0; i < sectionsSize; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case PERSONAL, OBJECTIVE -> sections.put(sectionType, new TextSection(dis.readUTF()));

                case ACHIEVEMENT, QUALIFICATIONS -> {
                    final int stringsCount = dis.readInt();
                    final ListSection listSection = new ListSection(new ArrayList<>());
                    final List<String> items = listSection.getItems();
                    for (int j = 0; j < stringsCount; j++) {
                        items.add(dis.readUTF());
                    }
                    sections.put(sectionType, listSection);
                }

                case EXPERIENCE, EDUCATION -> {
                    final int organizationsCount = dis.readInt();
                    List<Organization> organizations = new ArrayList<>();
                    for (int j = 0; j < organizationsCount; j++) {
                        String orgName = dis.readUTF();
                        String url = dis.readUTF();
                        url = url.equals("") ? null : url;
                        int positionsCount = dis.readInt();
                        List<Organization.Position> positions = readPositions(dis, positionsCount);
                        organizations.add(new Organization(new Link(orgName, url), positions));
                    }
                    sections.put(sectionType, new OrganizationSection(organizations));
                }
            }
        }
        return sections;
    }

    private List<Organization.Position> readPositions(DataInputStream dis, int positionsCount) throws IOException {
        List<Organization.Position> positions = new ArrayList<>(positionsCount);
        for (int po = 0; po < positionsCount; po++) {
            int startYear = dis.readInt();
            Month startMonth = Month.valueOf(dis.readUTF());
            int endYear = dis.readInt();
            Month endMonth = Month.valueOf(dis.readUTF());
            String title = dis.readUTF();
            String description = dis.readUTF();
            description = description.equals("") ? null : description;
            positions.add(new Organization.Position(startYear, startMonth, endYear, endMonth, title, description));
        }
        return positions;
    }
}
