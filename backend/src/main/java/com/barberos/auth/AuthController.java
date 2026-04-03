package com.barberos.auth;

import com.barberos.identity.UserDTO;
import com.barberos.identity.UserEntity;
import com.barberos.identity.UserResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserResolver userResolver;

    public AuthController(UserResolver userResolver) {
        this.userResolver = userResolver;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(401).build();
        }

        UserEntity user = userResolver.resolveUser(authentication);
        return ResponseEntity.ok(UserDTO.from(user));
    }
}
