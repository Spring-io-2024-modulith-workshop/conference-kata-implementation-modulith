CREATE TABLE IF NOT EXISTS users
(
    id    UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role  VARCHAR(50)  NOT NULL
);
