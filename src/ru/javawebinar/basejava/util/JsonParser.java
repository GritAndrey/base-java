package ru.javawebinar.basejava.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.javawebinar.basejava.model.Section;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.LocalDate;

public class JsonParser {
    private static final Type LOCAL_DATE_TYPE = new TypeToken<LocalDate>() {
    }.getType();
    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter<Section>())
            .registerTypeAdapter(LOCAL_DATE_TYPE, new GsonLocalDateAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }
    public static <T> T read(String object, Class<T> clazz) {
        return GSON.fromJson(object, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }
    public static  <T> String write(T object, Class<T> clazz){
        return GSON.toJson(object,clazz);
    }
}
