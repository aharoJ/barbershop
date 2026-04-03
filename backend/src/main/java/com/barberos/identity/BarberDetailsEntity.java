package com.barberos.identity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

/**
 * D24: Created lazily on explicit barber enrollment.
 * D30: specialties stored as PostgreSQL text[] with GIN index.
 * D31: visibility on BarberDetails, not Profile.
 * D33: profile_id is the PK via @MapsId — downstream barber_id FKs reference this.
 */
@Entity
@Table(name = "barber_details")
public class BarberDetailsEntity {

    @Id
    private UUID profileId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "accepting_new_clients", nullable = false)
    private boolean acceptingNewClients = true;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "specialties", columnDefinition = "text[]")
    private String[] specialties = {};

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BarberVisibility visibility = BarberVisibility.DRAFT;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected BarberDetailsEntity() {
    }

    public BarberDetailsEntity(ProfileEntity profile) {
        this.profile = profile;
    }

    @PrePersist
    void prePersist() {
        var now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = Instant.now();
    }

    public UUID getProfileId() { return profileId; }
    public ProfileEntity getProfile() { return profile; }
    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public boolean isAcceptingNewClients() { return acceptingNewClients; }
    public void setAcceptingNewClients(boolean acceptingNewClients) { this.acceptingNewClients = acceptingNewClients; }
    public String[] getSpecialties() { return specialties; }
    public void setSpecialties(String[] specialties) { this.specialties = specialties; }
    public BarberVisibility getVisibility() { return visibility; }
    public void setVisibility(BarberVisibility visibility) { this.visibility = visibility; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
