package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Contacts;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;

import java.util.Arrays;
import java.util.Map;
@SuppressWarnings({"rawtypes", "unchecked"})
public class ResumeTestData {
    public static void main(String[] args) {
        Resume testResume = new Resume("Test Name");

        Map<Contacts, String> resumeContacts = testResume.getContacts();
        resumeContacts.put(Contacts.EMAIL, "somemail@mail.ru");
        resumeContacts.put(Contacts.PHONE, "1234567");
        resumeContacts.put(Contacts.SKYPE, "skype of Test Name");
        resumeContacts.put(Contacts.SOCIAL, "Какая-то социальная сеть");

        Map<SectionType, Section> resumeSections = testResume.getSections();

        Arrays.stream(SectionType.values()).forEach(sectionType -> {
            resumeSections.put(sectionType, new SectionFactory(sectionType).getSection());
        });

        System.out.println(testResume.getFullName());
        resumeContacts.forEach((contact, contactValue) -> {
            System.out.print(contact.getContactType() + ": ");
            System.out.println(contactValue);
        });
        System.out.println("++++++++++++++++++++++++++++++++");
        resumeSections.forEach((section, content) -> {
            System.out.println(section.getTitle());
            content.getContent().forEach(System.out::println);
            System.out.println("-----------------------------");
        });


    }
}
