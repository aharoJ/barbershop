package com.barberos.identity.internal;

import java.util.Set;

/** D26: Reserved slugs to protect system routes. */
final class ReservedSlugs {

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

    private ReservedSlugs() {
    }

    static boolean isReserved(String slug) {
        return RESERVED.contains(slug);
    }
}
