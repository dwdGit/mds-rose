CREATE TABLE IF NOT EXISTS dataset_class (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(50) NOT NULL,
    marker VARCHAR(50) NOT NULL,
    color VARCHAR(50) NOT NULL,
    project_id INTEGER,
    FOREIGN KEY (project_id) REFERENCES project(id)
    );