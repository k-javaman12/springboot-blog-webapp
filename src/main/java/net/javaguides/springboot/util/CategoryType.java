package net.javaguides.springboot.util;

public enum CategoryType {
    QA("Q&A"),
    COMMUNITY("Community"),
    NOTICE("Notice");

    private final String displayName;

    CategoryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
