ALTER TABLE services DROP COLUMN description;
ALTER TABLE services DROP COLUMN price;
ALTER TABLE services DROP COLUMN type;

CREATE TABLE IF NOT EXISTS providers (
    id UUID PRIMARY KEY default gen_random_uuid(),
    name varchar,
    price float,
    sitter_id UUID NOT NULL,
    FOREIGN KEY (sitter_id) REFERENCES sitters(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE requests ADD COLUMN service_id UUID NOT NULL;
ALTER TABLE requests ADD CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES providers(id);