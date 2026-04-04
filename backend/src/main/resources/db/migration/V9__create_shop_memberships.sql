-- D34: 3-role enum (OWNER/MANAGER/STAFF), UNIQUE(shop_id, user_id)
-- D43: Soft revoke on shop deletion

CREATE TABLE shop_memberships (
    id                 UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    shop_id            UUID        NOT NULL REFERENCES shops(id) ON DELETE RESTRICT,
    user_id            UUID        NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    role               VARCHAR(20) NOT NULL CHECK (role IN ('OWNER', 'MANAGER', 'STAFF')),
    status             VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
                       CHECK (status IN ('ACTIVE', 'REVOKED')),
    revoked_at         TIMESTAMPTZ,
    revoked_by_user_id UUID        REFERENCES users(id) ON DELETE RESTRICT,
    created_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    UNIQUE (shop_id, user_id)
);

CREATE INDEX idx_memberships_shop_active ON shop_memberships(shop_id, role) WHERE status = 'ACTIVE';
CREATE INDEX idx_memberships_user ON shop_memberships(user_id) WHERE status = 'ACTIVE';
