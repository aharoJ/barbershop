-- D16: Multi-provider identity via join table (auth research R1, 3/3 unanimous)
-- Replaces single oauth2_provider/oauth2_subject columns on users table.

CREATE TABLE user_oauth2_identities (
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID         NOT NULL,
    provider   VARCHAR(50)  NOT NULL,
    subject    VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now(),

    CONSTRAINT fk_identities_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT uq_identities_provider_subject UNIQUE (provider, subject)
);

CREATE INDEX idx_identities_user_id ON user_oauth2_identities (user_id);
CREATE INDEX idx_identities_email ON user_oauth2_identities (LOWER(email));

-- Migrate existing OAuth2 data from users table to identities table
INSERT INTO user_oauth2_identities (user_id, provider, subject, email)
SELECT id, oauth2_provider, oauth2_subject, email
FROM users
WHERE oauth2_provider IS NOT NULL AND oauth2_subject IS NOT NULL;

-- Drop OAuth2 columns from users table
ALTER TABLE users DROP CONSTRAINT IF EXISTS uq_users_oauth2;
ALTER TABLE users DROP COLUMN IF EXISTS oauth2_provider;
ALTER TABLE users DROP COLUMN IF EXISTS oauth2_subject;
