package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.ContactsAdapter;
import ru.javawebinar.basejava.util.SectionsAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"uuid", "fullName", "contacts", "sections"})
public class Resume implements Comparable<Resume>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @XmlJavaTypeAdapter(value = ContactsAdapter.class)
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    @XmlJavaTypeAdapter(value = SectionsAdapter.class)
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
    private String uuid;
    private String fullName;


    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public String getContacts(ContactType type) {
        return contacts.get(type);
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public Section getSections(SectionType type) {
        return sections.get(type);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void addContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public void addSection(SectionType type, Section section) {
        sections.put(type, section);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid)
                && fullName.equals(resume.fullName)
                && Objects.equals(contacts, resume.contacts)
                && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        return fullName + " uuid: " + uuid + contacts + sections;
    }

    @Override
    public int compareTo(Resume that) {
        int fullNameCompare = this.fullName.compareTo(that.fullName);
        return fullNameCompare == 0
                ? this.uuid.compareTo(that.uuid)
                : fullNameCompare;
    }
}
