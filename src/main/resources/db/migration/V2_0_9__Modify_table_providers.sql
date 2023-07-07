ALTER TABLE providers
    DROP CONSTRAINT IF EXISTS providers_sitter_id_fkey;

ALTER TABLE providers
    ADD CONSTRAINT providers_sitter_id_fkey
        FOREIGN KEY (sitter_id) REFERENCES sitters(id)
        ON DELETE CASCADE;