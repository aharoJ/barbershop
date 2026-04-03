package com.barberos.identity.internal;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Locale;

/**
 * D26: Per-entity-type slug generation adapted from Stage.
 * Sanitize display name, try base, retry with random suffix on collision.
 */
@Component
public class SlugGenerator {

    private static final int MAX_RETRIES = 5;
    private static final int SUFFIX_LENGTH = 4;
    private static final String SUFFIX_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final ProfileRepository profileRepository;

    SlugGenerator(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public String generate(String displayName) {
        String base = sanitize(displayName);
        if (base.isEmpty()) {
            base = "user";
        }

        if (!ReservedSlugs.isReserved(base) && !profileRepository.existsBySlug(base)) {
            return base;
        }

        for (int i = 0; i < MAX_RETRIES; i++) {
            String candidate = base + "-" + randomSuffix(SUFFIX_LENGTH);
            if (!ReservedSlugs.isReserved(candidate) && !profileRepository.existsBySlug(candidate)) {
                return candidate;
            }
        }

        throw new IllegalStateException("Unable to generate unique slug after " + MAX_RETRIES + " attempts");
    }

    private static String sanitize(String input) {
        String sanitized = input.toLowerCase(Locale.ROOT).trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("[\\s-]+", "-")
                .replaceAll("^-|-$", "");
        return sanitized.substring(0, Math.min(sanitized.length(), 50));
    }

    private static String randomSuffix(int length) {
        var sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(SUFFIX_CHARS.charAt(RANDOM.nextInt(SUFFIX_CHARS.length())));
        }
        return sb.toString();
    }
}
