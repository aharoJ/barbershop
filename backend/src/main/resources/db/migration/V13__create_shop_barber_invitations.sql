-- D41: Separate invitation table for claimed barber consent flow

CREATE TABLE shop_barber_invitations (
    id                 UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    shop_id            UUID        NOT NULL REFERENCES shops(id) ON DELETE RESTRICT,
    barber_id          UUID        NOT NULL REFERENCES barber_details(profile_id) ON DELETE RESTRICT,
    invited_by_user_id UUID        NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    status             VARCHAR(20) NOT NULL DEFAULT 'PENDING'
                       CHECK (status IN ('PENDING', 'ACCEPTED', 'DECLINED', 'CANCELED', 'EXPIRED')),
    created_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    responded_at       TIMESTAMPTZ,
    expires_at         TIMESTAMPTZ
);

CREATE INDEX idx_invitations_barber_pending ON shop_barber_invitations(barber_id) WHERE status = 'PENDING';
CREATE INDEX idx_invitations_shop ON shop_barber_invitations(shop_id);
