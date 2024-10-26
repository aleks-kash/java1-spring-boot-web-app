package org.example.springbootapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Priority {
    LOW, MEDIUM, HIGH;

    @JsonCreator
    public static Priority fromValue(String value) {
        for (Priority priority : Priority.values()) {
            if (priority.name().equalsIgnoreCase(value)) {
                return priority;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid priority");
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
