package com.dg.mdsrose.project.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class CSVFileProcessor extends FileProcessor {
    public CSVFileProcessor(String fileName, Map<Integer, String> columnsToPick) {
        this.fileName = fileName;
        this.columnsToPick = columnsToPick;
    }

    public List<String> parseFile(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        lines.removeFirst();
        return lines;
    }

}
