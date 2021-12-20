package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataStreamStrategy implements SerializationStrategy {
    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            int contactsSize = dis.readInt();
            resume.getContacts().putAll(readContacts(dis, contactsSize));

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
            writeContacts(dos, contacts);

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            writeSections(dos, sections);
        }
    }

    private void writeContacts(DataOutputStream dos, Map<ContactType, String> contacts) throws IOException {
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    private void writeSections(DataOutputStream dos, Map<SectionType, Section> sections) throws IOException {
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            switch (entry.getKey()) {
                case PERSONAL, OBJECTIVE -> {
                    final TextSection textSection = (TextSection) entry.getValue();
                    dos.writeUTF(textSection.getContent().collect(Collectors.joining()));
                }
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
                    writeOrganizations(dos, organizations);
                }
            }
        }
    }

    private void writeOrganizations(DataOutputStream dos, List<Organization> organizations) throws IOException {
        for (Organization organization : organizations) {
            final Link homePage = organization.getHomePage();
            dos.writeUTF(homePage.getName());
            dos.writeUTF(homePage.getUrl() != null ? homePage.getUrl() : "");
            writePositions(dos, organization);
        }
    }

    private void writePositions(DataOutputStream dos, Organization organization) throws IOException {
        final List<Organization.Position> positions = organization.getPositions();
        dos.writeInt(positions.size());
        for (Organization.Position position : positions) {
            writePositionDates(dos, position.getStartDate(), position.getEndDate());
            dos.writeUTF(position.getTitle());
            dos.writeUTF(position.getDescription() != null ? position.getDescription() : "");
        }
    }

    private void writePositionDates(DataOutputStream dos, LocalDate... localDates) throws IOException {
        for (LocalDate localDate : localDates) {
            dos.writeInt(localDate.getYear());
            dos.writeUTF(localDate.getMonth().name());
        }
    }

    private Map<ContactType, String> readContacts(DataInputStream dis, int contactsSize) throws IOException {
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        for (int con = 0; con < contactsSize; con++) {
            contacts.put(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
        return contacts;
    }

    private Map<SectionType, Section> readSections(DataInputStream dis, int sectionsSize) throws IOException {
        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
        for (int i = 0; i < sectionsSize; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case PERSONAL, OBJECTIVE -> sections.put(sectionType, new TextSection(dis.readUTF()));
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    final int stringsCount = dis.readInt();
                    final ListSection listSection = new ListSection(new ArrayList<>(stringsCount));
                    final List<String> items = listSection.getItems();
                    for (int j = 0; j < stringsCount; j++) {
                        items.add(dis.readUTF());
                    }
                    sections.put(sectionType, listSection);
                }
                case EXPERIENCE, EDUCATION -> {
                    final int organizationsCount = dis.readInt();
                    List<Organization> organizations = readOrganizations(dis, organizationsCount);
                    sections.put(sectionType, new OrganizationSection(organizations));
                }
            }
        }
        return sections;
    }

    private List<Organization> readOrganizations(DataInputStream dis, int organizationsCount) throws IOException {
        List<Organization> organizations = new ArrayList<>();
        for (int j = 0; j < organizationsCount; j++) {
            String orgName = dis.readUTF();
            String url = dis.readUTF();
            url = url.equals("") ? null : url;
            int positionsCount = dis.readInt();
            List<Organization.Position> positions = readPositions(dis, positionsCount);
            organizations.add(new Organization(new Link(orgName, url), positions));
        }
        return organizations;
    }

    private List<Organization.Position> readPositions(DataInputStream dis, int positionsCount) throws IOException {
        List<Organization.Position> positions = new ArrayList<>(positionsCount);
        for (int po = 0; po < positionsCount; po++) {
            LocalDate[] positionDates = readPositionDates(dis);
            String title = dis.readUTF();
            String description = dis.readUTF();
            description = description.equals("") ? null : description;
            positions.add(new Organization.Position(positionDates[0], positionDates[1], title, description));
        }
        return positions;
    }

    private LocalDate[] readPositionDates(DataInputStream dis) throws IOException {
        int startYear = dis.readInt();
        Month startMonth = Month.valueOf(dis.readUTF());
        int endYear = dis.readInt();
        Month endMonth = Month.valueOf(dis.readUTF());
        return new LocalDate[]{
                DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth)
        };
    }
}
