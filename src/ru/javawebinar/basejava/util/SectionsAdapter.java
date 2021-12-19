package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SectionsAdapter extends XmlAdapter<SectionsAdapter.AdaptedMap, Map<SectionType, Section>> {

    @Override
    public EnumMap<SectionType, Section> unmarshal(AdaptedMap adaptedMap) {
        List<AdaptedEntry> adaptedEntries = adaptedMap.elements;
        EnumMap<SectionType, Section> map = new EnumMap<>(SectionType.class);
        for (AdaptedEntry AdaptedEntry : adaptedEntries) {
            map.put(AdaptedEntry.key, AdaptedEntry.value);
        }
        return map;
    }

    @Override
    public AdaptedMap marshal(Map<SectionType, Section> map) {
        AdaptedMap adaptedMap = new AdaptedMap();
        for (Map.Entry<SectionType, Section> entry : map.entrySet()) {
            AdaptedEntry AdaptedEntry = new AdaptedEntry();
            AdaptedEntry.key = entry.getKey();
            AdaptedEntry.value = entry.getValue();
            adaptedMap.elements.add(AdaptedEntry);
        }
        return adaptedMap;
    }

    @XmlType(name = "Section")
    public static class AdaptedMap {

        @XmlElement(name = "section")
        ArrayList<AdaptedEntry> elements = new ArrayList<>();
    }

    @XmlType(name = "Entry")
    public static class AdaptedEntry {

        @XmlAttribute(name = "sectionType")
        public SectionType key;

        @XmlElement(name = "value")
        public Section value;

    }

}
