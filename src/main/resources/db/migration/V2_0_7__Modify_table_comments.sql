ALTER TABLE comments
    DROP CONSTRAINT IF EXISTS comments_user_id_fkey,
    DROP CONSTRAINT IF EXISTS comments_post_id_fkey,
    DROP CONSTRAINT IF EXISTS comments_comment_id_fkey;

ALTER TABLE comments
    ADD CONSTRAINT comments_user_id_fkey
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE;

ALTER TABLE comments
    ADD CONSTRAINT comments_post_id_fkey
        FOREIGN KEY (post_id) REFERENCES posts(id)
        ON DELETE CASCADE;

ALTER TABLE comments
    ADD CONSTRAINT comments_comment_id_fkey
        FOREIGN KEY (comment_id) REFERENCES comments(id)
        ON DELETE CASCADE;