-- Replace case-sensitive email index and constraint with case-insensitive equivalents.
-- Application normalizes to lowercase, but this enforces it at the DB level.

ALTER TABLE users DROP CONSTRAINT uq_users_email;
DROP INDEX idx_users_email;

CREATE UNIQUE INDEX uq_users_email ON users (LOWER(email));
CREATE INDEX idx_users_email ON users (LOWER(email));
