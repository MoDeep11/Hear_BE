ALTER TABLE monthly_statistics
    ALTER COLUMN emotion_distribution SET DATA TYPE jsonb
    USING emotion_distribution::jsonb;