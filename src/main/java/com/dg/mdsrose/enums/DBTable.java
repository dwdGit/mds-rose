package com.dg.mdsrose.enums;

public enum DBTable {
    USER("user"),
    PROJECT("project"),
    DATASET_CLASS("dataset_class");

    private final String value;

    DBTable(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

