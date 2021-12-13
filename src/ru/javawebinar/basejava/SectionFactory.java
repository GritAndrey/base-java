package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.util.NoSuchElementException;

public class SectionFactory {
    private SectionType sectionType;

    public SectionFactory(SectionType sectionType) {
        this.sectionType = sectionType;
    }
@SuppressWarnings({"rawtypes"})
    public  Section getSection() {
        switch (sectionType) {
            case PERSONAL -> {
                return new StringSectionImpl("HardCoded PERSONAL Личные качества.");
            }
            case OBJECTIVE -> {
                return new StringSectionImpl("HardCoded OBJECTIVE Позиция.");
            }
            case ACHIEVEMENT -> {
                BulletedListSection listStringSection = new BulletedListSection();
                listStringSection.getElements().add("HardCoded ACHIEVEMENT(достижение) 1");
                listStringSection.getElements().add("HardCoded ACHIEVEMENT(достижение) 2");
                return listStringSection;
            }
            case QUALIFICATIONS -> {
                BulletedListSection bulletedListSection = new BulletedListSection();
                bulletedListSection.getElements().add("HardCoded QUALIFICATIONS(Квалификация) 1");
                bulletedListSection.getElements().add("HardCoded QUALIFICATIONS(Квалификация) 2");
                return bulletedListSection;
            }
            case EXPERIENCE -> {
                OrganizationListSection organizationListSection = new OrganizationListSection();
                organizationListSection.getElements().add(new Experience("org1","www.site1.org","do some work 1"));
                organizationListSection.getElements().add(new Experience("org2","www.site2.org","do some work 2"));
                organizationListSection.getElements().add(new Experience("org3","www.site3.org","do some work 3"));
                return organizationListSection;
            }
            case EDUCATION -> {
                OrganizationListSection orgListSection = new OrganizationListSection();
                orgListSection.getElements().add(new Experience("edu1","www.site1.edu","learn 1"));
                orgListSection.getElements().add(new Experience("edu2","www.site2.edu","learn 2"));
                orgListSection.getElements().add(new Experience("edu3","www.site3.edu","learn 3"));
                return orgListSection;
            }
            default -> throw new NoSuchElementException();
        }
    }
}
