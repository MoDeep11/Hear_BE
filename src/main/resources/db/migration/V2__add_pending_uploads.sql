CREATE TABLE pending_uploads
(
    id           UUID        NOT NULL,
    user_id      UUID        NOT NULL,
    s3_key       VARCHAR(512) NOT NULL,
    service_type VARCHAR(32) NOT NULL,
    expired_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_pending_uploads PRIMARY KEY (id)
);

CREATE INDEX idx_pending_uploads_expired_at ON pending_uploads (expired_at);
