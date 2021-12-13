package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.NoSuchElementException;

public class SectionFactory {
    private SectionType sectionType;

    public SectionFactory(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    @SuppressWarnings({"rawtypes"})
    public Section getSection() {
        switch (sectionType) {
            case PERSONAL -> {
                return new TextSection("HardCoded PERSONAL Личные качества.");
            }
            case OBJECTIVE -> {
                return new TextSection("HardCoded OBJECTIVE Позиция.");
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
                organizationListSection.getElements().add(new Experience("org1", "www.site1.org",
                        DateUtil.of(2000, Month.DECEMBER), DateUtil.of(2001, Month.DECEMBER), "title1", "do some work 1"));
                organizationListSection.getElements().add(new Experience("org2", "www.site2.org",
                        DateUtil.of(2002, Month.FEBRUARY), DateUtil.of(2005, Month.MARCH), "title2", "do some work 2"));
                organizationListSection.getElements().add(new Experience("org3", "www.site3.org",
                        DateUtil.of(2005, Month.AUGUST), LocalDate.now(), "title3", "do some work 3"));

                return organizationListSection;
            }
            case EDUCATION -> {
                OrganizationListSection organizationListSection = new OrganizationListSection();
                organizationListSection.getElements().add(new Experience("edu1", "www.site1.edu",
                        DateUtil.of(1995, Month.SEPTEMBER), DateUtil.of(1999, Month.DECEMBER), "titleEdu1", null));
                organizationListSection.getElements().add(new Experience("edu2", "www.site2.edu",
                        DateUtil.of(2002, Month.FEBRUARY), DateUtil.of(2002, Month.JUNE), "titleEdu2", null));
                organizationListSection.getElements().add(new Experience("edu3", "www.site3.edu",
                        DateUtil.of(2010, Month.APRIL), DateUtil.of(2010, Month.JULY), "titleEdu3", null));
                return organizationListSection;
            }
            default -> throw new NoSuchElementException();
        }
    }
}
