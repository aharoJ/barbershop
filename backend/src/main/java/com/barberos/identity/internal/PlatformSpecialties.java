package com.barberos.identity.internal;

import java.util.Set;

/**
 * C43: Platform-defined closed vocabulary for barber specialties.
 * Application layer validates against this list.
 */
public final class PlatformSpecialties {

    private static final Set<String> ALLOWED = Set.of(
            "fade", "taper", "buzz-cut", "crew-cut", "pompadour",
            "beard-trim", "beard-shape", "hot-towel-shave", "straight-razor",
            "line-up", "edge-up", "hair-design", "hair-color",
            "afro", "braids", "dreadlocks", "twists",
            "kids-cut", "senior-cut", "womens-cut",
            "scalp-treatment", "hair-wash", "conditioning"
    );

    private PlatformSpecialties() {
    }

    public static void validate(String[] specialties) {
        if (specialties == null) return;
        if (specialties.length > 20) {
            throw new IllegalArgumentException("Maximum 20 specialties allowed");
        }
        for (int i = 0; i < specialties.length; i++) {
            String s = specialties[i];
            if (s == null || s.isBlank()) {
                throw new IllegalArgumentException("Specialty cannot be blank");
            }
            String normalized = s.toLowerCase(java.util.Locale.ROOT).trim();
            if (!ALLOWED.contains(normalized)) {
                throw new IllegalArgumentException("Unknown specialty: " + s);
            }
            specialties[i] = normalized;
        }
    }

    public static Set<String> allowed() {
        return ALLOWED;
    }
}
