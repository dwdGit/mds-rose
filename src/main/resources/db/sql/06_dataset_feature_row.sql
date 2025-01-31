CREATE TABLE IF NOT EXISTS dataset_feature_row (
    dataset_row_id INTEGER NOT NULL,
    dataset_feature_id INTEGER NOT NULL,
    value DOUBLE NOT NULL,
    FOREIGN KEY (dataset_row_id) REFERENCES dataset_row(id)
    FOREIGN KEY (dataset_feature_id) REFERENCES dataset_feature(id)
);