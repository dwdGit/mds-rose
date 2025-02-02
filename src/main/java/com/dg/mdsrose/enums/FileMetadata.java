package com.dg.mdsrose.enums;

public enum FileMetadata {
    DATA(".data", ","),
    CSV(".csv", ",");

    private final String extension;
    private final String delimiter;

    FileMetadata(String extension, String delimiter) {
        this.extension = extension;
        this.delimiter = delimiter;
    }

    public String getExtension() {
        return extension;
    }

    public String getDelimiter() {
        return delimiter;
    }
}
