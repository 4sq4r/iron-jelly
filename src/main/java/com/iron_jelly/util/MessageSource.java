package com.iron_jelly.util;

public enum MessageSource {
    USERNAME_ALREADY_EXISTS("Username already exists."),
    USER_NOT_FOUND("User not found.");

    private String text;

    MessageSource(String text) {
        this.text = text;
    }

    public String getText(String... params) {
        return String.format(this.text, params);
    }
}
