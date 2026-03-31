ALTER TABLE monthly_statistics
    DROP CONSTRAINT pk_monthly_statistics;

ALTER TABLE monthly_statistics
    DROP COLUMN id;

ALTER TABLE monthly_statistics
    ADD CONSTRAINT pk_monthly_statistics PRIMARY KEY (user_id, target_year_month);
