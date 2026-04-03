package com.barberos.shared;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimitFilter extends OncePerRequestFilter {

    private static final int MAX_TRACKED_IPS = 10_000;

    private final int maxRequests;
    private final String pathPrefix;
    private final ConcurrentHashMap<String, Window> windows = new ConcurrentHashMap<>();

    public RateLimitFilter(int maxRequestsPerMinute, String pathPrefix) {
        this.maxRequests = maxRequestsPerMinute;
        this.pathPrefix = pathPrefix;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        long currentMinute = System.currentTimeMillis() / 60_000;

        Window window = windows.compute(ip, (key, existing) -> {
            if (existing == null || existing.minute != currentMinute) {
                return new Window(currentMinute);
            }
            return existing;
        });

        int count = window.count.incrementAndGet();

        if (count > maxRequests) {
            response.setStatus(429);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Retry-After", "60");
            response.getWriter().write("{\"error\":\"Too many requests\"}");
            return;
        }

        filterChain.doFilter(request, response);

        if (windows.size() > MAX_TRACKED_IPS) {
            evictStaleEntries(currentMinute);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String method = request.getMethod();
        if ("GET".equals(method) || "HEAD".equals(method) || "OPTIONS".equals(method)) {
            return true;
        }
        return !request.getRequestURI().startsWith(pathPrefix);
    }

    private void evictStaleEntries(long currentMinute) {
        windows.entrySet().removeIf(entry -> entry.getValue().minute != currentMinute);
    }

    static class Window {
        final long minute;
        final AtomicInteger count;

        Window(long minute) {
            this.minute = minute;
            this.count = new AtomicInteger(0);
        }
    }
}
