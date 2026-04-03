CREATE TABLE users (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email           VARCHAR(255) NOT NULL,
    name            VARCHAR(255) NOT NULL,
    oauth2_provider VARCHAR(50),
    oauth2_subject  VARCHAR(255),
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ  NOT NULL DEFAULT now(),

    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT uq_users_oauth2 UNIQUE (oauth2_provider, oauth2_subject)
);

CREATE INDEX idx_users_email ON users (email);
