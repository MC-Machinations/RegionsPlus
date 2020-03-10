package me.machinemaker.regionsplus.flags;

public enum StringFlag {
    ENTER_CHAT("", "Message to show when entering a region"),
    ENTER_TITLE("", "Title/subtitle to show when entering a region"),
    LEAVE_CHAT("", "Message to show when leaving a region"),
    LEAVE_TITLE("", "Title/subtitle to show when leaving a region"),
    ENTER_DENY_CHAT("", "Message to show when entry is denied"),
    EXIT_DENY_CHAT("", "Message to show when leaving is denied");

    String defaultString;
    String description;

    StringFlag(String defaultString, String desc) {
        this.defaultString = defaultString;
        this.description = desc;
    }

    public String getDefaultValue() {
        return defaultString;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

    public String toUpper() {
        return this.name().toUpperCase();
    }
}
