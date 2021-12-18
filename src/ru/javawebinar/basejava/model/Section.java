package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.stream.Stream;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Section implements Serializable {

    public abstract <T> Stream<T> getContent();
}
