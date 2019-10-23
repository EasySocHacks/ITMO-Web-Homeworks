package ru.itmo.tpl.util;

public enum UserColor {
    RED("red"),
    GREEN("green"),
    BLUE("blue");

    private final String color;

    UserColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
