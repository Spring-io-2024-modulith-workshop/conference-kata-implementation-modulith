CREATE TABLE IF NOT EXISTS proposals
(
    id                 UUID                  DEFAULT gen_random_uuid() PRIMARY KEY,
    title              VARCHAR(255) NOT NULL,
    description        VARCHAR(255) NOT NULL,
    speaker_id         UUID         NOT NULL,
    creation_date_time TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    status             VARCHAR(255) NOT NULL DEFAULT 'NEW'
);

INSERT INTO proposals (id, title, description, speaker_id)
VALUES ('00000000-0000-0000-0000-000000000001', 'Test Proposal 1', 'Test Description 1', '00000000-0000-0000-0000-000000000001')
ON CONFLICT (id) DO NOTHING;
