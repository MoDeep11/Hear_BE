ALTER TABLE diaries
    ADD CONSTRAINT uc_diaries_chat_id UNIQUE (chat_id);