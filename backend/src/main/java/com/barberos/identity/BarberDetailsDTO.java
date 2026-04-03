package com.barberos.identity;

import java.util.Arrays;
import java.util.List;

public record BarberDetailsDTO(
        Integer experienceYears,
        String licenseNumber,
        boolean acceptingNewClients,
        List<String> specialties,
        BarberVisibility visibility
) {
    public static BarberDetailsDTO from(BarberDetailsEntity entity) {
        return new BarberDetailsDTO(
                entity.getExperienceYears(),
                entity.getLicenseNumber(),
                entity.isAcceptingNewClients(),
                entity.getSpecialties() != null ? Arrays.asList(entity.getSpecialties()) : List.of(),
                entity.getVisibility()
        );
    }
}
