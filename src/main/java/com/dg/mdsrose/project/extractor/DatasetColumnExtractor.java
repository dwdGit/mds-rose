package com.dg.mdsrose.project.extractor;

public abstract class DatasetColumnExtractor<T> {
    protected final String path;

    public DatasetColumnExtractor(String path) {
        this.path = path;
    }

    public abstract T extractColumnsMetadata();
}
