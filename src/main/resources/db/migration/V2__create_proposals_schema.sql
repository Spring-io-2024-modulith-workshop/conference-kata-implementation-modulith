CREATE TABLE IF NOT EXISTS proposals
(
    id                 UUID                  DEFAULT gen_random_uuid() PRIMARY KEY,
    title              VARCHAR(255) NOT NULL,
    description        VARCHAR(255) NOT NULL,
    speaker_id         UUID         NOT NULL,
    creation_date_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status             VARCHAR(255) NOT NULL DEFAULT 'NEW'
);
