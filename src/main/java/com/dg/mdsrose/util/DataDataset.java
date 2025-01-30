package com.dg.mdsrose.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataDataset {
    private static final String DELIMETER = ",";

    private final String path;

    public DataDataset(String path) {
        this.path = path;
    }

    public Integer getNumberOfColumns() {
        int numberOfColumns = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String headerLine = br.readLine();
            numberOfColumns = headerLine.split(DELIMETER).length;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return numberOfColumns;
    }

}
