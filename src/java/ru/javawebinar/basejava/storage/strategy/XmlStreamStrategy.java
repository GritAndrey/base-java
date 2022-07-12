package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.XMLParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamStrategy implements SerializationStrategy {
    private XMLParser xmlParser;

    public XmlStreamStrategy() {
        this.xmlParser = new XMLParser(
                Resume.class, TextSection.class, ListSection.class, Link.class,
                OrganizationSection.class, Organization.class, Organization.Position.class);

    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }

    @Override
    public void write(OutputStream os, Resume resume) throws IOException {
        try (Writer r = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, r);
        }
    }
}
