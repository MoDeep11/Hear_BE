ALTER TABLE diary_images
    ADD COLUMN IF NOT EXISTS chat_id UUID;

CREATE INDEX IF NOT EXISTS idx_diary_images_chat_id
    ON diary_images(chat_id);