CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY default gen_random_uuid(),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR,
    password VARCHAR,
    date_of_birth TIMESTAMP,
    user_role VARCHAR(50),
    user_img_url VARCHAR,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)