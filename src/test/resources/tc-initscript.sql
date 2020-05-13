-- TODO 5 utworzenie schematu i populacja bazy
DROP TABLE IF EXISTS responses;
CREATE TABLE responses (
                        id SERIAL PRIMARY KEY,
                        greeting VARCHAR(30),
                        local_date_time  TIMESTAMP
);

INSERT INTO responses VALUES (1, 'Hello, World!', '2022-01-19 19:10:25-07');