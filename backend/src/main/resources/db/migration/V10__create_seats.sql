-- D36: Named seats for capacity management

CREATE TABLE seats (
    id         UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    shop_id    UUID        NOT NULL REFERENCES shops(id) ON DELETE RESTRICT,
    name       VARCHAR(100) NOT NULL,
    status     VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
               CHECK (status IN ('ACTIVE', 'MAINTENANCE', 'REMOVED')),
    removed_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    UNIQUE (shop_id, name)
);

CREATE INDEX idx_seats_shop_active ON seats(shop_id) WHERE status = 'ACTIVE';
