CREATE TABLE IF NOT EXISTS posts (
    id UUID PRIMARY KEY default gen_random_uuid(),
    title VARCHAR,
    content TEXT,
    image VARCHAR,
    views INT,
    comments INT,
    likes INT,
    author UUID NOT NULL,
    FOREIGN KEY (author) REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tags (
    id UUID PRIMARY KEY default gen_random_uuid(),
    name varchar(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS categories (
    id UUID PRIMARY KEY default gen_random_uuid(),
    post_id UUID NOT NULL,
    tag_id UUID NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

