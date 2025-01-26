package com.dg.mdsrose.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataDataset {


    private final String path;
    private final String delimiter = ",";


    public DataDataset(String path) {
        this.path = path;
    }

    public Integer getNumberOfColumns(){
        int numberOfColumns = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String headerLine = br.readLine();
            numberOfColumns = headerLine.split(delimiter).length;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        return numberOfColumns;
    }

}
