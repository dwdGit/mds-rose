package com.dg.mdsrose.dataset;

import com.dg.mdsrose.view.CsvSelectColumn;

public class CsvStrategy implements DatasetStrategy {


    @Override
    public void showFrame(String path) {
        new CsvSelectColumn(path);
    }

}
