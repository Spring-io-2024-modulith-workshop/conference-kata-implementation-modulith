insert into users(id, name, email, phone, role)
VALUES ('5768d8a7-bdb0-4022-9fb0-fa7c68491287', 'admin', 'admin@gmail.com',
        '555-3465678', 'ORGANIZER');

insert into proposals(id, title, description, speaker_id, creation_date_time,
                      status)
VALUES ('2da9d8c5-b9ab-43b7-bb43-5b82ca8754d4', 'sample proposal',
        'sample description', '5768d8a7-bdb0-4022-9fb0-fa7c68491287',
        '2020-01-01 10:00:00', 'NEW');
