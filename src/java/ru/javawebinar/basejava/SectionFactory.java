package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.Month;
import java.util.NoSuchElementException;

public class SectionFactory {
    private final SectionType sectionType;

    public SectionFactory(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    public Section getSection() {
        switch (sectionType) {
            case PERSONAL -> {
                return new TextSection("HardCoded PERSONAL Личные качества.");
            }
            case OBJECTIVE -> {
                return new TextSection("HardCoded OBJECTIVE Позиция.");
            }
            case ACHIEVEMENT -> {

                return new ListSection(
                        "HardCoded ACHIEVEMENT(достижение)" + (int) (Math.random() * 100),
                        "HardCoded ACHIEVEMENT(достижение)" + (int) (Math.random() * 100));
            }
            case QUALIFICATIONS -> {
                return new ListSection("HardCoded QUALIFICATIONS(Квалификация)" + (int) (Math.random() * 100),
                        "HardCoded QUALIFICATIONS(Квалификация)" + (int) (Math.random() * 100));

            }
            case EXPERIENCE -> {
                Organization org1 = new Organization("org" + (int) (Math.random() * 100), "www.site1.org",
                        DateUtil.of(2000, Month.DECEMBER), DateUtil.of(2001, Month.DECEMBER), "title1", "do some work 1");
                Organization org2 = new Organization("org" + (int) (Math.random() * 100), "www.site2.org",
                        DateUtil.of(2002, Month.FEBRUARY), DateUtil.of(2005, Month.MARCH), "title2", "do some work 2");
                Organization org3 = new Organization("org" + (int) (Math.random() * 100), "www.site3.org",
                        DateUtil.of(2005, Month.AUGUST), DateUtil.of(2010, Month.AUGUST), "title3", "do some work 3");
                return new OrganizationSection(org1, org2, org3);
            }
            case EDUCATION -> {
                Organization edu1 = new Organization("edu" + (int) (Math.random() * 100), "www.site1.edu",
                        DateUtil.of(1995, Month.SEPTEMBER), DateUtil.of(1999, Month.DECEMBER), "titleEdu1", null);

                Organization edu2 = new Organization("edu" + (int) (Math.random() * 100), "www.site2.edu",
                        DateUtil.of(2002, Month.FEBRUARY), DateUtil.of(2002, Month.JUNE), "titleEdu2", null);

                Organization edu3 = new Organization("edu" + (int) (Math.random() * 100), "www.site3.edu",
                        DateUtil.of(2010, Month.APRIL), DateUtil.of(2010, Month.JULY), "titleEdu3", null);

                OrganizationSection organizationSection = new OrganizationSection(edu1, edu2, edu3);
                organizationSection.getOrganizations().get(0)
                        .addWorkPosition(DateUtil.of(1999, Month.DECEMBER),
                                DateUtil.of(2000, Month.FEBRUARY), "titleEdu1Position2", null);

                return organizationSection;
            }
            default -> throw new NoSuchElementException();
        }
    }
}
