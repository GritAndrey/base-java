package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;

import java.util.Arrays;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = generateResume("testUuid", "testName");

        System.out.println(resume.getFullName());
        resume.getContacts().forEach((contact, contactValue) -> {
            System.out.print(contact.getContactType() + ": ");
            System.out.println(contactValue);
        });
        System.out.println("++++++++++++++++++++++++++++++++");
        resume.getSections().forEach((section, content) -> {
            System.out.println(section.getTitle());
            content.getContent().forEach(System.out::println);
            System.out.println("-----------------------------");
        });

    }

    public static Resume generateResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        Map<ContactType, String> resumeContacts = resume.getContacts();
        Map<SectionType, Section> resumeSections = resume.getSections();

        resumeContacts.put(ContactType.PHONE, "1234567");
        resumeContacts.put(ContactType.MOBILE, "7654321");
        resumeContacts.put(ContactType.HOME_PHONE, "123-123-12");
        resumeContacts.put(ContactType.SKYPE, "skype_number");
        resumeContacts.put(ContactType.MAIL, "somemail@mail.ru");
        resumeContacts.put(ContactType.LINKEDIN, "linkedin page");
        resumeContacts.put(ContactType.GITHUB, "github page");
        resumeContacts.put(ContactType.STACKOVERFLOW, "stackoverflow profile");
        resumeContacts.put(ContactType.HOME_PAGE, "домашняя страница");

        Arrays.stream(SectionType.values()).forEach(sectionType -> {
            resumeSections.put(sectionType, new SectionFactory(sectionType).getSection());
        });
        return resume;
    }
}
