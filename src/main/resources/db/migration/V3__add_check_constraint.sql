ALTER TABLE monthly_statistics
    ADD CONSTRAINT ck_monthly_statistics_target_year_month
        CHECK (target_year_month = date_trunc('month', target_year_month)::date);