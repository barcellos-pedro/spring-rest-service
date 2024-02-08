package com.pedro.rest.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private String displayName;

    Status() {
    }

    Status(String status) {
        this.displayName = status;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
