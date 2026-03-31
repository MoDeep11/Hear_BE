ALTER TABLE user_stats
    ALTER COLUMN last_written_at TYPE DATE;

CREATE INDEX idx_user_diary_created ON diaries (user_id, created_at DESC);
