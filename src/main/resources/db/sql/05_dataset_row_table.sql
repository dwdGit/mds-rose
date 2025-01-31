CREATE TABLE IF NOT EXISTS dataset_row (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    class_id INTEGER NOT NULL,
    x DOUBLE NOT NULL,
    y DOUBLE NOT NULL,
    project_id INTEGER,
    FOREIGN KEY (class_id) REFERENCES dataset_class(id)
    FOREIGN KEY (project_id) REFERENCES project(id)
);