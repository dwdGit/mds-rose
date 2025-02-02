package com.dg.mdsrose.project.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class CSVFileProcessor extends FileProcessor {
    public CSVFileProcessor(String filePath, Map<Integer, String> columnsToPick) {
        this.filePath = filePath;
        this.columnsToPick = columnsToPick;
    }

    public List<String> parseFile() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        lines.removeFirst();
        return lines;
    }

}
