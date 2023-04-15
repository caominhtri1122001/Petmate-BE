CREATE TABLE IF NOT EXISTS posts (
    id UUID PRIMARY KEY default gen_random_uuid(),
    title VARCHAR NOT NULL,
    content TEXT NOT NULL,
    image VARCHAR NOT NULL,
    views INT NOT NULL DEFAULT 0,
    comments INT NOT NULL DEFAULT 0,
    likes INT NOT NULL DEFAULT 0,
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
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS comments (
    id UUID PRIMARY KEY default gen_random_uuid(),
    content TEXT NOT NULL,
    user_id UUID NOT NULL,
    post_id UUID NOT NULL,
    comment_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (comment_id) REFERENCES comments(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

