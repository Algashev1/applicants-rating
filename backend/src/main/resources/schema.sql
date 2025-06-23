DROP TABLE IF EXISTS training_direction;
DROP TABLE IF EXISTS institutes CASCADE;

CREATE TABLE institutes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS training_direction (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255),
  institute_id BIGINT REFERENCES institutes(id)
);