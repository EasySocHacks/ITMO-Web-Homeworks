package ru.itmo.tpl.model;

public class Link {
    private final String uri;
    private final String name;

    public Link(String uri, String name) {
        this.uri = uri;
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }
}
