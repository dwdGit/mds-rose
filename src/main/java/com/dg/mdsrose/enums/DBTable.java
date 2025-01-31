package com.dg.mdsrose.enums;

public enum DBTable {
    USER("user"),
    PROJECT("project"),
    DATASET_CLASS("dataset_class"),
    DATASET_FEATURE_ROW("dataset_feature_row"),
    DATASET_ROW("dataset_row"),
    DATASET_FEATURE("dataset_feature");

    private final String value;

    DBTable(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

