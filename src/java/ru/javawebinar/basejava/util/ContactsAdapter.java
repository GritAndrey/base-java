package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.ContactType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ContactsAdapter extends XmlAdapter<ContactsAdapter.AdaptedMap, Map<ContactType, String>> {

    @Override
    public EnumMap<ContactType, String> unmarshal(AdaptedMap adaptedMap) {
        List<AdaptedEntry> adaptedEntries = adaptedMap.elements;
        EnumMap<ContactType, String> map = new EnumMap<>(ContactType.class);
        for (AdaptedEntry adaptedEntry : adaptedEntries) {
            map.put(adaptedEntry.key, adaptedEntry.value);
        }
        return map;
    }

    @Override
    public AdaptedMap marshal(Map<ContactType, String> map) {
        AdaptedMap adaptedMap = new AdaptedMap();
        for (Map.Entry<ContactType, String> entry : map.entrySet()) {
            AdaptedEntry adaptedEntry = new AdaptedEntry();
            adaptedEntry.key = entry.getKey();
            adaptedEntry.value = entry.getValue();
            adaptedMap.elements.add(adaptedEntry);
        }
        return adaptedMap;
    }

    public static class AdaptedMap {

        @XmlElement(name = "contact")
        ArrayList<AdaptedEntry> elements = new ArrayList<>();

    }

    public static class AdaptedEntry {

        @XmlAttribute(name = "contactType")
        public ContactType key;

        @XmlElement
        public String value;

    }

}
