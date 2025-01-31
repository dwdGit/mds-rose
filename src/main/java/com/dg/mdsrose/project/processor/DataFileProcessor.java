package com.dg.mdsrose.project.processor;

import java.util.Map;

public class DataFileProcessor extends FileProcessor {
    public DataFileProcessor(String fileName, Map<Integer, String> columnsToPick) {
        this.fileName = fileName;
        this.columnsToPick = columnsToPick;
    }
}
