package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.EnumMap;
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
                case PERSONAL, OBJECTIVE -> entry.getValue().getContent().forEach(o -> {
                    try {
                        dos.writeUTF(o.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    dos.writeInt((int) entry.getValue().getContent().count());
                    entry.getValue().getContent().forEach(o -> {
                        try {
                            dos.writeUTF(o.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                case EXPERIENCE, EDUCATION -> {
                }

            }
        }
    }

    private Map<SectionType, Section> readSections(DataInputStream dis, int sectionsSize) throws IOException {
        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
        for (int i = 0; i < sectionsSize; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {

                case PERSONAL, OBJECTIVE -> {
                    sections.put(sectionType, new TextSection(dis.readUTF()));
                }

                case ACHIEVEMENT, QUALIFICATIONS -> {
                    final int stringsCount = dis.readInt();
                    final ListSection listSection = new ListSection(new ArrayList<>());
                    for (int j = 0; j < stringsCount; j++) {
                        listSection.getItems().add(dis.readUTF());
                    }
                    sections.put(sectionType, listSection);
                }
                case EXPERIENCE, EDUCATION -> {
                }
            }
        }
        return sections;
    }
}
