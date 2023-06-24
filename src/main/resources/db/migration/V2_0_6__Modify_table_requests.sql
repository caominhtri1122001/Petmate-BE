ALTER TABLE requests ADD COLUMN valid boolean default false;

UPDATE requests SET valid = false;