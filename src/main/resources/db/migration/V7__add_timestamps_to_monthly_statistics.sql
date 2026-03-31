ALTER TABLE monthly_statistics
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN updated_at TIMESTAMP WITHOUT TIME ZONE;

UPDATE monthly_statistics
SET created_at = NOW(),
    updated_at = NOW();

ALTER TABLE monthly_statistics
    ALTER COLUMN created_at SET NOT NULL,
    ALTER COLUMN updated_at SET NOT NULL;
