-- D34: No owner_id — ownership via shop_memberships only
-- D38: Structured address + lat/lng (PostGIS deferred to Phase 11 — ARM64 image unavailable)
-- D40: 4-state status enum + separate soft delete

CREATE TABLE shops (
    id                UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    name              VARCHAR(255) NOT NULL,
    slug              VARCHAR(100) NOT NULL UNIQUE,
    description       TEXT,
    phone_number      VARCHAR(50),
    email             VARCHAR(255),
    website           VARCHAR(500),
    address_line_1    TEXT,
    address_line_2    TEXT,
    city              TEXT,
    state_region      TEXT,
    postal_code       TEXT,
    country_code      VARCHAR(2),
    formatted_address TEXT,
    latitude          DOUBLE PRECISION,
    longitude         DOUBLE PRECISION,
    timezone          VARCHAR(50) NOT NULL,
    logo_url          VARCHAR(500),
    cover_photo_url   VARCHAR(500),
    status            VARCHAR(20) NOT NULL DEFAULT 'DRAFT'
                      CHECK (status IN ('DRAFT', 'ACTIVE', 'SUSPENDED', 'CLOSED')),
    is_deleted        BOOLEAN     NOT NULL DEFAULT false,
    deleted_at        TIMESTAMPTZ,
    version           BIGINT      NOT NULL DEFAULT 0,
    created_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
    CHECK (latitude IS NULL OR (latitude BETWEEN -90 AND 90)),
    CHECK (longitude IS NULL OR (longitude BETWEEN -180 AND 180))
);

CREATE INDEX idx_shops_slug ON shops(slug);
CREATE INDEX idx_shops_city_country ON shops(city, country_code) WHERE is_deleted = false;
CREATE INDEX idx_shops_active ON shops(id) WHERE status = 'ACTIVE' AND is_deleted = false;
