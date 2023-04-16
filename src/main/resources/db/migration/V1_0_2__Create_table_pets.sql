CREATE TABLE IF NOT EXISTS pets (
    id UUID PRIMARY KEY default gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    species VARCHAR(50),
    breed VARCHAR(50),
    age INT DEFAULT 0,
    gender BOOLEAN NOT NULL DEFAULT TRUE,
    weight INT,
    pet_img_url VARCHAR,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);