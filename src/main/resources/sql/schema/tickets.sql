Create TABLE IF NOT EXISTS tickets
(
    id       UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    date     VARCHAR(50) NOT NULL
)