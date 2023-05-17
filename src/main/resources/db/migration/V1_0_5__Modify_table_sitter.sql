ALTER TABLE sitters ADD COLUMN latitude float;
ALTER TABLE sitters ADD COLUMN longitude float;
ALTER TABLE sitters DROP COLUMN city;
ALTER TABLE sitters DROP COLUMN postcode;