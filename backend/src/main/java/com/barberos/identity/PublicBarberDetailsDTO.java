package com.barberos.identity;

import java.util.Arrays;
import java.util.List;

/** Public barber details — excludes licenseNumber (PII). */
public record PublicBarberDetailsDTO(
        Integer experienceYears,
        boolean acceptingNewClients,
        List<String> specialties,
        BarberVisibility visibility
) {
    public static PublicBarberDetailsDTO from(BarberDetailsEntity entity) {
        return new PublicBarberDetailsDTO(
                entity.getExperienceYears(),
                entity.isAcceptingNewClients(),
                entity.getSpecialties() != null ? Arrays.asList(entity.getSpecialties()) : List.of(),
                entity.getVisibility()
        );
    }
}
