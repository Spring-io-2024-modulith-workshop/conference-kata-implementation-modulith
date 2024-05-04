insert into users(id, name, email, phone, role)
VALUES ('51d205e8-d274-4923-9623-c4f23aea0ce5', 'admin', 'admin@gmail.com',
        '555-3465678', 'ORGANIZER');

insert into proposals(id, title, description, speaker_id, creation_date_time,
                      status)
VALUES ('43736d30-28c0-4e2c-9c1d-f8e37c929f0e', 'sample proposal',
        'sample description', '51d205e8-d274-4923-9623-c4f23aea0ce5',
        '2020-01-01 10:00:00', 'NEW');
