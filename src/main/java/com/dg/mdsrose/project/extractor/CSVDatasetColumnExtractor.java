package com.dg.mdsrose.project.extractor;

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.dg.mdsrose.enums.FileMetadata.CSV;

public class CSVDatasetColumnExtractor extends DatasetColumnExtractor<List<Pair<Integer, String>>> {
    public CSVDatasetColumnExtractor(String path) {
        super(path);
    }

    @Override
    public List<Pair<Integer, String>> extractColumnsMetadata() {
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
        return columnNames;
    }
}
