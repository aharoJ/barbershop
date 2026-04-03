-- D29: Soft delete + PII anonymization
-- D32: Ghost/proxy user support
ALTER TABLE users ADD COLUMN deleted_at  TIMESTAMPTZ;
ALTER TABLE users ADD COLUMN is_deleted  BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE users ADD COLUMN is_claimed  BOOLEAN NOT NULL DEFAULT true;

CREATE INDEX idx_users_active ON users (id) WHERE is_deleted = false;
CREATE INDEX idx_users_unclaimed ON users (email) WHERE is_claimed = false;
