CREATE TABLE IF NOT EXISTS comments (
    id UUID PRIMARY KEY default gen_random_uuid(),
    content TEXT,
    user_id UUID NOT NULL,
    post_id UUID NOT NULL,
    comment_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (comment_id) REFERENCES comments(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);