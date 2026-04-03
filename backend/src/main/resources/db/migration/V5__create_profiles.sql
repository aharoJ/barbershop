-- D23: Eager profile creation (1:1 with users)
-- D26: Per-entity-type slug uniqueness
-- D27: displayName separate from User.name
-- D32: Ghost/proxy profile support (claimed, created_by_user_id)
CREATE TABLE profiles (
    id                 UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id            UUID         NOT NULL UNIQUE REFERENCES users(id),
    display_name       VARCHAR(255) NOT NULL,
    bio                TEXT,
    photo_url          VARCHAR(500),
    slug               VARCHAR(100) NOT NULL UNIQUE,
    phone_number       VARCHAR(50),
    timezone           VARCHAR(50),
    claimed            BOOLEAN      NOT NULL DEFAULT true,
    created_by_user_id UUID         REFERENCES users(id),
    created_at         TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at         TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX idx_profiles_user_id ON profiles (user_id);
CREATE INDEX idx_profiles_slug ON profiles (slug);
CREATE INDEX idx_profiles_unclaimed ON profiles (claimed) WHERE claimed = false;
