ALTER TABLE providers ADD COLUMN service_type VARCHAR;

UPDATE providers SET name = 'Pet Grooming' WHERE name = 'Pet Gromming';
UPDATE providers SET service_type = 'PER SERVING' WHERE name = 'Dog Walking';
UPDATE providers SET service_type = 'PER SERVING' WHERE name = 'Doggy Day Care';
UPDATE providers SET service_type = 'PER SERVING' WHERE name = 'Drop-In Visits';
UPDATE providers SET service_type = 'PER SERVING' WHERE name = 'Pet Grooming';
UPDATE providers SET service_type = 'PER DAY' WHERE name = 'Boarding';
UPDATE providers SET service_type = 'PER DAY' WHERE name = 'House Sitting';