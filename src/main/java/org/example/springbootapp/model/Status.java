package org.example.springbootapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    PENDING, IN_PROGRESS, COMPLETED;

    @JsonCreator
    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid status");
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
