CREATE TABLE IF NOT EXISTS tickets
(
    id       UUID                 DEFAULT gen_random_uuid() PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    date     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    price    DECIMAL     NOT NULL,
    status   VARCHAR(50) NOT NULL
);
