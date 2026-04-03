package com.barberos.auth;

import com.barberos.auth.internal.MagicLinkService;
import com.barberos.identity.UserEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/magic-link")
public class MagicLinkController {

    private final MagicLinkService magicLinkService;

    public MagicLinkController(MagicLinkService magicLinkService) {
        this.magicLinkService = magicLinkService;
    }

    @PostMapping("/request")
    public ResponseEntity<Map<String, String>> requestLink(@Valid @RequestBody MagicLinkRequest request) {
        magicLinkService.sendMagicLink(request.email());
        return ResponseEntity.ok(Map.of("message", "If that email is registered or valid, a magic link has been sent."));
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verify(@Valid @RequestBody VerifyRequest verifyRequest,
                                                      jakarta.servlet.http.HttpServletRequest request) {
        UserEntity user = magicLinkService.verifyToken(verifyRequest.token(), request.getRemoteAddr());
        magicLinkService.establishSession(user, request);
        return ResponseEntity.ok(Map.of("redirectUrl", magicLinkService.getDashboardUrl()));
    }

    public record MagicLinkRequest(@NotBlank @Email String email) {
    }

    public record VerifyRequest(@NotBlank String token) {
    }
}
