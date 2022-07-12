package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class DataStreamStrategy implements SerializationStrategy {
    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            resume.getContacts().putAll(readContacts(dis));
            resume.getSections().putAll(readSections(dis));
            return resume;
        }
    }

    @Override
    public void write(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeContacts(dos, resume.getContacts());
            writeSections(dos, resume.getSections());
        }
    }

    private void writeContacts(DataOutputStream dos, Map<ContactType, String> contacts) throws IOException {
        writeCollection(contacts.entrySet(), dos, entry -> {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        });
    }

    private void writeSections(DataOutputStream dos, Map<SectionType, Section> sections) throws IOException {
        writeCollection(sections.entrySet(), dos, entry -> {
            dos.writeUTF(entry.getKey().name());
            switch (entry.getKey()) {
                case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) entry.getValue()).getContent().collect(Collectors.joining()));
                case ACHIEVEMENT, QUALIFICATIONS -> writeCollection(((ListSection) entry.getValue()).getItems(), dos, dos::writeUTF);
                case EXPERIENCE, EDUCATION -> writeOrganizations(dos, ((OrganizationSection) entry.getValue()).getOrganizations());
            }
        });
    }

    private void writeOrganizations(DataOutputStream dos, List<Organization> organizations) throws IOException {
        writeCollection(organizations, dos, organization -> {
            final Link homePage = organization.getHomePage();
            dos.writeUTF(homePage.getName());
            final String url = homePage.getUrl();
            dos.writeUTF(url != null ? url : "");
            writePositions(dos, organization);
        });

    }

    private void writePositions(DataOutputStream dos, Organization organization) throws IOException {
        final List<Organization.Position> positions = organization.getPositions();
        writeCollection(positions, dos, position -> {
            writePositionDate(dos, position.getStartDate());
            writePositionDate(dos, position.getEndDate());
            dos.writeUTF(position.getTitle());
            final String description = position.getDescription();
            dos.writeUTF(description != null ? description : "");
        });
    }

    private void writePositionDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeUTF(localDate.getMonth().name());
    }

    private Map<ContactType, String> readContacts(DataInputStream dis) throws IOException {
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        readElements(dis, () -> contacts.put(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
        return contacts;
    }

    private Map<SectionType, Section> readSections(DataInputStream dis) throws IOException {
        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
        readElements(dis, () -> {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case PERSONAL, OBJECTIVE -> sections.put(sectionType, new TextSection(dis.readUTF()));
                case ACHIEVEMENT, QUALIFICATIONS -> sections.put(sectionType, new ListSection(readList(dis, dis::readUTF)));
                case EXPERIENCE, EDUCATION -> sections.put(sectionType, new OrganizationSection(readOrganizations(dis)));
            }
        });
        return sections;
    }

    private List<Organization> readOrganizations(DataInputStream dis) throws IOException {
        return readList(dis, () -> {
            String orgName = dis.readUTF();
            String url = dis.readUTF();
            url = url.equals("") ? null : url;
            List<Organization.Position> positions = readPositions(dis);
            return new Organization(new Link(orgName, url), positions);
        });
    }

    private List<Organization.Position> readPositions(DataInputStream dis) throws IOException {
        return readList(dis, () -> {
            LocalDate startDate = readPositionDate(dis);
            LocalDate endDate = readPositionDate(dis);
            String title = dis.readUTF();
            String description = dis.readUTF();
            description = description.equals("") ? null : description;
            return new Organization.Position(startDate, endDate, title, description);
        });
    }

    private LocalDate readPositionDate(DataInputStream dis) throws IOException {
        int year = dis.readInt();
        Month month = Month.valueOf(dis.readUTF());
        return DateUtil.of(year, month);
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream dos, ElementWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private void readElements(DataInputStream dis, ElementProcessor processor) throws IOException {
        int mapSize = dis.readInt();
        for (int i = 0; i < mapSize; i++) {
            processor.process();
        }
    }

    private interface ElementProcessor {
        void process() throws IOException;
    }

    private interface ElementWriter<T> {
        void write(T element) throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }
}
