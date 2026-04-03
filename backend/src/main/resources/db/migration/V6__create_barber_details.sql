-- D24: Explicit barber enrollment
-- D30: Specialties as text[] with GIN index
-- D31: Three-state visibility on BarberDetails
-- D33: profile_id as PK (downstream barber_id references this)
CREATE TABLE barber_details (
    profile_id            UUID PRIMARY KEY REFERENCES profiles(id),
    experience_years      INTEGER,
    license_number        VARCHAR(100),
    accepting_new_clients BOOLEAN      NOT NULL DEFAULT true,
    specialties           TEXT[]       NOT NULL DEFAULT '{}',
    visibility            VARCHAR(20)  NOT NULL DEFAULT 'DRAFT'
                          CHECK (visibility IN ('DRAFT', 'LISTED', 'UNLISTED')),
    created_at            TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at            TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX idx_barber_details_specialties ON barber_details USING GIN (specialties);
CREATE INDEX idx_barber_details_listed ON barber_details (profile_id) WHERE visibility = 'LISTED';
