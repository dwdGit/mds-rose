package com.dg.mdsrose.dataset;

import com.dg.mdsrose.view.DataSelectColumn;


public class DataStrategy implements DatasetStrategy {


    @Override
    public void showFrame(String path) {
        new DataSelectColumn(path);
    }
}
