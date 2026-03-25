CREATE TABLE ai_image_tasks
(
    id         UUID        NOT NULL,
    session_id UUID,
    diary_id   UUID,
    status     VARCHAR(16) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_ai_image_tasks PRIMARY KEY (id)
);

CREATE TABLE calendar
(
    calendar_date date         NOT NULL,
    day_of_week   VARCHAR(255) NOT NULL,
    is_holiday    BOOLEAN      NOT NULL,
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

CREATE TABLE diaries
(
    id          UUID          NOT NULL,
    user_id     UUID          NOT NULL,
    content     VARCHAR(1000) NOT NULL,
    emotion     VARCHAR(8)    NOT NULL,
    tags        JSONB,
    source_type VARCHAR(16)   NOT NULL,
    session_id  UUID,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_diaries PRIMARY KEY (id)
);

CREATE TABLE diary_ai_comments
(
    diary_id   UUID        NOT NULL,
    content    VARCHAR(1000),
    status     VARCHAR(16) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_diary_ai_comments PRIMARY KEY (diary_id)
);

CREATE TABLE diary_images
(
    id            UUID        NOT NULL,
    diary_id      UUID,
    image_url     VARCHAR(512),
    display_order INTEGER     NOT NULL,
    source_type   VARCHAR(16) NOT NULL,
    status        VARCHAR(16) NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_diary_images PRIMARY KEY (id)
);

CREATE TABLE messages
(
    id           UUID          NOT NULL,
    session_id   UUID          NOT NULL,
    sender       VARCHAR(8)    NOT NULL,
    message      VARCHAR(1000) NOT NULL,
    message_type VARCHAR(8)    NOT NULL,
    voice_url    VARCHAR(512),
    duration     BIGINT,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_messages PRIMARY KEY (id)
);

CREATE TABLE monthly_statistics
(
    id                   UUID    NOT NULL,
    user_id              UUID    NOT NULL,
    target_year_month    date    NOT NULL,
    diary_count          INTEGER NOT NULL,
    photo_count          INTEGER NOT NULL,
    writing_rate         FLOAT   NOT NULL,
    ai_report_content    VARCHAR(1000),
    emotion_distribution JSONB   NOT NULL,
    CONSTRAINT pk_monthly_statistics PRIMARY KEY (id)
);

CREATE TABLE user_calendars
(
    has_diary     BOOLEAN NOT NULL,
    emotion       VARCHAR(8),
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    calendar_date date    NOT NULL,
    user_id       UUID    NOT NULL,
    CONSTRAINT pk_user_calendars PRIMARY KEY (calendar_date, user_id)
);

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
    current_streak  INTEGER,
    total_diaries   INTEGER NOT NULL,
    max_streak      INTEGER NOT NULL,
    last_written_at TIMESTAMP WITHOUT TIME ZONE,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_user_stats PRIMARY KEY (user_id)
);

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

ALTER TABLE diary_images
    ADD CONSTRAINT FK_DIARY_IMAGES_ON_DIARY FOREIGN KEY (diary_id) REFERENCES diaries (id);

ALTER TABLE messages
    ADD CONSTRAINT FK_MESSAGES_ON_SESSION FOREIGN KEY (session_id) REFERENCES chats (id);

ALTER TABLE monthly_statistics
    ALTER COLUMN emotion_distribution SET DATA TYPE jsonb
    USING emotion_distribution::jsonb;

ALTER TABLE monthly_statistics
    ALTER COLUMN emotion_distribution SET DATA TYPE jsonb
    USING emotion_distribution::jsonb;

ALTER TABLE diaries
    ALTER COLUMN tags SET DATA TYPE jsonb
    USING tags::jsonb;