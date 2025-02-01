package com.dg.mdsrose.project.extractor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.dg.mdsrose.enums.FileMetadata.DATA;

public class DataDatasetColumnExtractor extends DatasetColumnExtractor<Integer> {
    public DataDatasetColumnExtractor(String path) {
        super(path);
    }

    @Override
    public Integer extractColumnsMetadata() {
        int numberOfColumns = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String headerLine = br.readLine();
            numberOfColumns = headerLine.split(DATA.getDelimiter()).length;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return numberOfColumns;
    }
}
