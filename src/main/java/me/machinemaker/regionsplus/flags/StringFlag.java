package me.machinemaker.regionsplus.flags;

import lombok.Getter;

public enum StringFlag implements IFlag<String> {
    ENTER_CHAT("", "Message to show when entering a region"),
    ENTER_TITLE("", "Title/subtitle to show when entering a region"),
    LEAVE_CHAT("", "Message to show when leaving a region"),
    LEAVE_TITLE("", "Title/subtitle to show when leaving a region"),
    ENTER_DENY_CHAT("", "Message to show when entry is denied"),
    EXIT_DENY_CHAT("", "Message to show when leaving is denied");

    final String defaultValue;
    @Getter
    final String description;

    StringFlag(String defaultString, String desc) {
        this.defaultValue = defaultString;
        this.description = desc;
    }

    public String getDefault() {
        return this.defaultValue;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

    public String toUpper() {
        return this.name().toUpperCase();
    }
}
