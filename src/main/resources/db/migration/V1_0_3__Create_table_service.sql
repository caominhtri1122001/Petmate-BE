CREATE TABLE IF NOT EXISTS services (
    id UUID PRIMARY KEY default gen_random_uuid(),
    name VARCHAR,
    description VARCHAR,
    price FLOAT,
    type VARCHAR,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)