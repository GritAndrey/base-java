package ru.javawebinar.basejava.model;

public enum ContactsType {
    PHONE("Тел"),
    SKYPE("Skype"),
    EMAIL("Почта"),
    SOCIAL("Социальная сеть");

    private final String contactType;

    ContactsType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactType() {
        return contactType;
    }
}
