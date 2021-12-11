package ru.javawebinar.basejava.model;

import java.util.NoSuchElementException;

public class SectionFactory {
    private SectionType sectionType;

    public SectionFactory(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    public Section getSection() {
        switch (sectionType) {
            case PERSONAL -> {
                return new StringSectionImpl("HardCoded PERSONAL Личные качества.");
            }
            case OBJECTIVE -> {
                return new StringSectionImpl("HardCoded OBJECTIVE Позиция.");
            }
            case ACHIEVEMENT -> {
                ListStringSectionImpl listStringSection = new ListStringSectionImpl();
                listStringSection.addContentElem("HardCoded ACHIEVEMENT(достижение) 1");
                listStringSection.addContentElem("HardCoded ACHIEVEMENT(достижение) 2");
                return listStringSection;
            }
            case QUALIFICATIONS -> {
                ListStringSectionImpl listStringSection = new ListStringSectionImpl();
                listStringSection.addContentElem("HardCoded QUALIFICATIONS(Квалификация) 1");
                listStringSection.addContentElem("HardCoded QUALIFICATIONS(Квалификация) 2");
                return listStringSection;
            }
            case EXPERIENCE -> {
                OrgListSectionImpl orgListSection = new OrgListSectionImpl();
                orgListSection.addWorkPoint(new WorkPoint("org1","www.site1.org","do some work 1"));
                orgListSection.addWorkPoint(new WorkPoint("org2","www.site2.org","do some work 2"));
                orgListSection.addWorkPoint(new WorkPoint("org3","www.site3.org","do some work 3"));
                return orgListSection;
            }
            case EDUCATION -> {
                OrgListSectionImpl orgListSection = new OrgListSectionImpl();
                orgListSection.addWorkPoint(new WorkPoint("edu1","www.site1.edu","learn 1"));
                orgListSection.addWorkPoint(new WorkPoint("edu2","www.site2.edu","learn 2"));
                orgListSection.addWorkPoint(new WorkPoint("edu3","www.site3.edu","learn 3"));
                return orgListSection;
            }
            default -> throw new NoSuchElementException();
        }
    }
}
