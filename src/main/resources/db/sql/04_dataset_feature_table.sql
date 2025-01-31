CREATE TABLE IF NOT EXISTS dataset_feature (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(50) NOT NULL,
    project_id INTEGER,
    FOREIGN KEY (project_id) REFERENCES project(id)
);