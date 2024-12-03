package com.iron_jelly.util;

public enum MessageSource {
    USERNAME_ALREADY_EXISTS("Username already exists."),
    USER_NOT_FOUND("User not found."),
    CARD_ALREADY_EXISTS("Card already exist"),
    CARD_NOT_FOUND("Card not found"),
    CARD_TEMPLATE_NOT_FOUND("Card template not found"),
    COMPANY_NOT_FOUND("Company not found"),
    ORDER_NOT_FOUND("Order not found");

    private String text;

    MessageSource(String text) {
        this.text = text;
    }

    public String getText(String... params) {
        return String.format(this.text, params);
    }
}
