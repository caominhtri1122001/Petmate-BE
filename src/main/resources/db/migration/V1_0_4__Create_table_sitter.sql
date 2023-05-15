ALTER TABLE users ADD COLUMN gender BOOLEAN DEFAULT FALSE;
ALTER TABLE users ADD COLUMN phone varchar(10);

CREATE TABLE IF NOT EXISTS sitters (
    id UUID PRIMARY KEY default gen_random_uuid(),
    address VARCHAR,
    city VARCHAR,
    postcode VARCHAR,
    yearOfExperience int,
    description VARCHAR,
    user_id UUID NOT NULL,
    status BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);