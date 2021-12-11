package ru.javawebinar.basejava.model;

public enum Contacts {
    PHONE("Тел"),
    SKYPE("Skype"),
    EMAIL("Почта"),
    SOCIAL("Социальная сеть");

    private String contactType;


    Contacts(String contactType) {
        this.contactType = contactType;
    }

    public String getContactType() {
        return contactType;
    }
}
