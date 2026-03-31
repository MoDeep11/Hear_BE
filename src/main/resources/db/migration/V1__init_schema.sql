CREATE TABLE users
(
    id                  UUID         NOT NULL,
    email               VARCHAR(100) NOT NULL,
    password            VARCHAR(100) NOT NULL,
    role                VARCHAR(8)   NOT NULL,
    status              VARCHAR(16)  NOT NULL,
    is_email_subscribed BOOLEAN      NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

CREATE TABLE user_profiles
(
    user_id           UUID         NOT NULL,
    nickname          VARCHAR(20)  NOT NULL,
    profile_image_url VARCHAR(255) NOT NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_user_profiles PRIMARY KEY (user_id)
);

CREATE TABLE user_stats
(
    user_id         UUID    NOT NULL,
    current_streak  INTEGER NOT NULL DEFAULT 0,
    total_diaries   INTEGER NOT NULL DEFAULT 0,
    max_streak      INTEGER NOT NULL DEFAULT 0,
    last_written_at DATE,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_user_stats PRIMARY KEY (user_id)
);

CREATE TABLE monthly_statistics
(
    user_id              UUID             NOT NULL,
    target_year_month    DATE             NOT NULL,
    diary_count          INTEGER          NOT NULL DEFAULT 0,
    photo_count          INTEGER          NOT NULL DEFAULT 0,
    writing_rate         DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    ai_report_content    VARCHAR(1000),
    emotion_distribution JSONB            NOT NULL,
    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_monthly_statistics PRIMARY KEY (user_id, target_year_month),
    CONSTRAINT ck_monthly_statistics_target_year_month
        CHECK (target_year_month = date_trunc('month', target_year_month)::date)
);

CREATE TABLE user_calendars
(
    calendar_date DATE      NOT NULL,
    user_id       UUID      NOT NULL,
    has_diary     BOOLEAN   NOT NULL DEFAULT false,
    emotion       VARCHAR(8),
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_user_calendars PRIMARY KEY (calendar_date, user_id)
);

CREATE TABLE calendar
(
    calendar_date DATE         NOT NULL,
    day_of_week   VARCHAR(255) NOT NULL,
    is_holiday    BOOLEAN      NOT NULL DEFAULT false,
    CONSTRAINT pk_calendar PRIMARY KEY (calendar_date)
);

CREATE TABLE chats
(
    id         UUID        NOT NULL,
    user_id    UUID        NOT NULL,
    status     VARCHAR(16) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_chats PRIMARY KEY (id)
);

CREATE TABLE messages
(
    id           UUID          NOT NULL,
    chat_id      UUID          NOT NULL,
    sender       VARCHAR(8)    NOT NULL,
    message      VARCHAR(1000) NOT NULL,
    message_type VARCHAR(8)    NOT NULL,
    voice_url    VARCHAR(512),
    duration     BIGINT,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_messages PRIMARY KEY (id)
);

ALTER TABLE messages
    ADD CONSTRAINT FK_MESSAGES_ON_CHAT FOREIGN KEY (chat_id) REFERENCES chats (id);

CREATE TABLE ai_image_tasks
(
    id         UUID        NOT NULL,
    chat_id    UUID,
    diary_id   UUID,
    status     VARCHAR(16) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_ai_image_tasks PRIMARY KEY (id)
);

CREATE TABLE diaries
(
    id          UUID          NOT NULL,
    user_id     UUID          NOT NULL,
    content     VARCHAR(1000) NOT NULL,
    emotion     VARCHAR(8)    NOT NULL,
    tags        JSONB         NOT NULL,
    source_type VARCHAR(16)   NOT NULL,
    chat_id     UUID,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_diaries PRIMARY KEY (id)
);

CREATE TABLE diary_images
(
    id            UUID        NOT NULL,
    diary_id      UUID,
    chat_id       UUID,
    image_url     VARCHAR(512),
    display_order INTEGER     NOT NULL DEFAULT 0,
    source_type   VARCHAR(16) NOT NULL,
    status        VARCHAR(16) NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_diary_images PRIMARY KEY (id)
);

ALTER TABLE diary_images
    ADD CONSTRAINT FK_DIARY_IMAGES_ON_DIARY FOREIGN KEY (diary_id) REFERENCES diaries (id);

CREATE TABLE diary_ai_comments
(
    diary_id   UUID         NOT NULL,
    content    VARCHAR(1000),
    status     VARCHAR(16)  NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_diary_ai_comments PRIMARY KEY (diary_id)
);

ALTER TABLE diary_ai_comments
    ADD CONSTRAINT FK_DIARY_AI_COMMENTS_ON_DIARY FOREIGN KEY (diary_id) REFERENCES diaries (id);

CREATE INDEX idx_user_diary_created ON diaries (user_id, created_at DESC);
CREATE INDEX idx_diary_images_chat_id ON diary_images (chat_id);
