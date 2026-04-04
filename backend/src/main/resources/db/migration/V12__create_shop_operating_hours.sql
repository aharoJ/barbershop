-- D37: Shop operating hours as local TIME (not UTC)
-- Multiple rows per day allowed for split shifts
-- day_of_week: 1=Monday, 7=Sunday (ISO 8601 / java.time.DayOfWeek)

CREATE TABLE shop_operating_hours (
    id          UUID     PRIMARY KEY DEFAULT gen_random_uuid(),
    shop_id     UUID     NOT NULL REFERENCES shops(id) ON DELETE RESTRICT,
    day_of_week SMALLINT NOT NULL CHECK (day_of_week BETWEEN 1 AND 7),
    opens_at    TIME     NOT NULL,
    closes_at   TIME     NOT NULL,
    CHECK (opens_at < closes_at)
);

CREATE INDEX idx_shop_hours_shop_day ON shop_operating_hours(shop_id, day_of_week);
