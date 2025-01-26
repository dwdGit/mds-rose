package com.dg.mdsrose.util;

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CsvDataset {

    private final String path;
    private final String delimiter = ",";

    public CsvDataset(String path) {
        this.path = path;
    }

    public Optional<List<Pair<Integer,String>>> getColumns(){
        List<Pair<Integer,String>> columnNames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String headerLine = br.readLine();
            String[] split = headerLine.split(delimiter);
            for (int i = 0; i < split.length-1; i++) {
                columnNames.add(Pair.of(i, split[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(columnNames);
    }
}
