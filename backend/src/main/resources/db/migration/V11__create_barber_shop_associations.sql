-- D35: Multi-shop barbers, historical rows, partial unique index
-- D36: Nullable default_seat_id for capacity management
-- D33: barber_id FK -> barber_details(profile_id)

CREATE TABLE barber_shop_associations (
    id               UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    barber_id        UUID        NOT NULL REFERENCES barber_details(profile_id) ON DELETE RESTRICT,
    shop_id          UUID        NOT NULL REFERENCES shops(id) ON DELETE RESTRICT,
    default_seat_id  UUID        REFERENCES seats(id) ON DELETE SET NULL,
    joined_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
    left_at          TIMESTAMPTZ,
    ended_reason     VARCHAR(20) CHECK (ended_reason IN ('BARBER_LEFT', 'SHOP_REMOVED', 'SHOP_CLOSED', 'SYSTEM_CLEANUP')),
    ended_by_user_id UUID        REFERENCES users(id) ON DELETE RESTRICT,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
    CHECK (left_at IS NULL OR left_at >= joined_at)
);

CREATE UNIQUE INDEX ux_active_barber_shop ON barber_shop_associations(barber_id, shop_id)
    WHERE left_at IS NULL;
CREATE INDEX idx_assoc_barber_active ON barber_shop_associations(barber_id) WHERE left_at IS NULL;
CREATE INDEX idx_assoc_shop_active ON barber_shop_associations(shop_id) WHERE left_at IS NULL;
