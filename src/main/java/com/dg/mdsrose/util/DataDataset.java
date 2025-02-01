package com.dg.mdsrose.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.dg.mdsrose.enums.FileMetadata.DATA;

public class DataDataset {
    private final String path;

    public DataDataset(String path) {
        this.path = path;
    }

    public Integer getNumberOfColumns() {
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
