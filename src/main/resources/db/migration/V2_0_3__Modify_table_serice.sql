ALTER TABLE services ADD COLUMN service_type VARCHAR;

UPDATE services SET name = 'Pet Grooming' WHERE name = 'Pet Gromming';
UPDATE services SET service_type = 'PER SERVING' WHERE name = 'Dog Walking';
UPDATE services SET service_type = 'PER SERVING' WHERE name = 'Doggy Day Care';
UPDATE services SET service_type = 'PER SERVING' WHERE name = 'Drop-In Visits';
UPDATE services SET service_type = 'PER SERVING' WHERE name = 'Pet Grooming';
UPDATE services SET service_type = 'PER DAY' WHERE name = 'Boarding';
UPDATE services SET service_type = 'PER DAY' WHERE name = 'House Sitting';