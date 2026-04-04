package com.barberos.shop;

import com.barberos.shop.internal.ShopRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Set;

/**
 * D26: Per-entity-type slug uniqueness with typed URLs (/shops/{slug}).
 * Adapted from Identity's SlugGenerator for shop namespace.
 * C49: Same accepted risk — PostgreSQL tx abort makes in-tx retry impossible.
 */
@Component
public class ShopSlugGenerator {

    private static final int MAX_RETRIES = 5;
    private static final int SUFFIX_LENGTH = 4;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";

    private final ShopRepository shopRepository;

    public ShopSlugGenerator(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public String generate(String name) {
        String base = sanitize(name);
        if (base.isEmpty()) {
            base = "shop";
        }

        if (!isReserved(base) && !shopRepository.existsBySlug(base)) {
            return base;
        }

        for (int i = 0; i < MAX_RETRIES; i++) {
            String candidate = base + "-" + randomSuffix();
            if (!isReserved(candidate) && !shopRepository.existsBySlug(candidate)) {
                return candidate;
            }
        }
        throw new IllegalStateException("Unable to generate unique shop slug after " + MAX_RETRIES + " attempts");
    }

    private static String sanitize(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }
        String sanitized = input.toLowerCase(Locale.ROOT).trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("[\\s-]+", "-")
                .replaceAll("^-|-$", "");
        return sanitized.substring(0, Math.min(sanitized.length(), 50));
    }

    private static String randomSuffix() {
        var sb = new StringBuilder(SUFFIX_LENGTH);
        for (int i = 0; i < SUFFIX_LENGTH; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    private static final Set<String> RESERVED = Set.of(
            "admin", "api", "auth", "barber", "barbers", "shop", "shops",
            "search", "discover", "book", "booking", "schedule", "payment",
            "about", "help", "support", "terms", "privacy", "login", "logout",
            "signup", "register", "user", "users", "profile", "profiles",
            "me", "dashboard", "settings", "billing", "docs", "status", "health",
            "explore", "trending", "new", "pro", "free", "null", "undefined",
            "test", "root", "home", "index", "feed", "sitemap", "ee",
            "account", "notifications", "reviews", "catalog", "appointments",
            "deleted"
    );

    private static boolean isReserved(String slug) {
        return RESERVED.contains(slug);
    }
}
