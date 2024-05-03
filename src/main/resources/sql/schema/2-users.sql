CREATE TABLE IF NOT EXISTS users
(
    id   UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(50)  NOT NULL
);

INSERT INTO users (id, name, email, phone, role)
VALUES ('00000000-0000-0000-0000-000000000001', 'testName', 'test@gmail.com', '1234567890', 'SPEAKER')
ON CONFLICT (id) DO NOTHING;