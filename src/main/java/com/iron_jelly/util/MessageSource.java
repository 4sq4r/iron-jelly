package com.iron_jelly.util;

public enum MessageSource {
    USERNAME_ALREADY_EXISTS("Username already exists."),
    USER_NOT_FOUND("User not found."),
    USERNAME_CANNOT_BE_EMPTY("Username cannot be empty."),
    CARD_ALREADY_EXISTS("Card already exist"),
    CARD_NOT_FOUND("Card not found"),
    CARD_TEMPLATE_NOT_FOUND("Card template not found"),
    COMPANY_NOT_FOUND("Company not found, id: %s"),
    COMPANY_NAME_TOO_LONG("Company name should be no more than 30 characters: %s"),
    ORDER_NOT_FOUND("Order not found"),
    ACCESS_DENIED("Access denied. Only for admins."),
    CARD_NOT_ACTIVE("Карта неактивна, чтобы сделать заказ воспользуйтесь другой картой");

    private String text;

    MessageSource(String text) {
        this.text = text;
    }

    public String getText(String... params) {
        return String.format(this.text, params);
    }
}
