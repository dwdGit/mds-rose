package com.dg.mdsrose.util;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class DatasetFileFilter extends FileFilter {


    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        return f.isFile() && (f.getName().endsWith(".data") || f.getName().endsWith(".csv"));
    }

    @Override
    public String getDescription() {
    return "DATA file (.csv, .data)";
    }
}
