ALTER TABLE diaries
    ALTER COLUMN tags SET DATA TYPE jsonb
    USING tags::jsonb;