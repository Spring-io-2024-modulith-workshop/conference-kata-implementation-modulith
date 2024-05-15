CREATE TABLE IF NOT EXISTS votes
(
    id                 UUID               DEFAULT gen_random_uuid() PRIMARY KEY,
    proposal_id        UUID      NOT NULL REFERENCES proposals (id),
    user_id            UUID      NOT NULL REFERENCES users (id),
    creation_date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    rating             INT                DEFAULT 0
);
