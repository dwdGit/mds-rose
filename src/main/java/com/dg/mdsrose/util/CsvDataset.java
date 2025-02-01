package com.dg.mdsrose.util;

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dg.mdsrose.enums.FileMetadata.CSV;

public class CsvDataset {
    private final String path;

    public CsvDataset(String path) {
        this.path = path;
    }

    public Optional<List<Pair<Integer,String>>> getColumns(){
        List<Pair<Integer,String>> columnNames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String headerLine = br.readLine();
            String[] split = headerLine.split(CSV.getDelimiter());
            for (int i = 0; i < split.length-1; i++) {
                columnNames.add(Pair.of(i, split[i]));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return Optional.of(columnNames);
    }
}
