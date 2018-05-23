package com.github.stodorov.entities;

public enum UserFeedback {
    LIKE("Like"),
    DISLIKE("Dislike");

    private String value;

    UserFeedback(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
