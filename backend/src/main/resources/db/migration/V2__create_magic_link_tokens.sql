CREATE TABLE magic_link_tokens (
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    token_hash VARCHAR(255)  NOT NULL,
    email      VARCHAR(255)  NOT NULL,
    expires_at TIMESTAMPTZ   NOT NULL,
    used_at    TIMESTAMPTZ,
    created_at TIMESTAMPTZ   NOT NULL DEFAULT now(),

    CONSTRAINT uq_magic_link_token_hash UNIQUE (token_hash)
);

CREATE INDEX idx_magic_link_tokens_email_created ON magic_link_tokens (email, created_at);
