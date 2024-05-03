CREATE TABLE IF NOT EXISTS votes
(
    id                 UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    proposal_id        UUID NOT NULL REFERENCES proposals (id),
    user_id            UUID NOT NULL REFERENCES users (id),
    creation_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    rating               INT       DEFAULT 0
);

alter table votes
    drop constraint if exists votes_pk;

alter table votes
    ADD CONSTRAINT votes_pk
        unique (proposal_id, user_id);

