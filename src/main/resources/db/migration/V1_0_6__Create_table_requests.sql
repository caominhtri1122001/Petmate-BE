CREATE TABLE IF NOT EXISTS requests (
    id UUID PRIMARY KEY default gen_random_uuid(),
    user_id UUID NOT NULL,
    sitter_id UUID NOT NULL,
    pet_id UUID NOT NULL,
    start_date DATE,
    end_date DATE,
    start_time TIME,
    end_time TIME,
    message VARCHAR,
    address VARCHAR,
    status BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (sitter_id) REFERENCES sitters(id) ON DELETE CASCADE,
    FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);