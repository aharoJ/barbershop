package com.barberos.identity;

import com.barberos.shared.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserResolver {

    private final UserRepository userRepository;
    private final UserOAuth2IdentityRepository identityRepository;

    public UserResolver(UserRepository userRepository,
                        UserOAuth2IdentityRepository identityRepository) {
        this.userRepository = userRepository;
        this.identityRepository = identityRepository;
    }

    public UUID resolveUserId(Authentication authentication) {
        return resolveUser(authentication).getId();
    }

    /**
     * Resolve the authenticated user. Rejects deleted and unclaimed (ghost) users.
     * C44: Ghost users cannot authenticate until claimed.
     * C47: Soft-deleted users filtered from active queries.
     */
    public UserEntity resolveUser(Authentication authentication) {
        UserEntity user;

        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User oauth2User = oauthToken.getPrincipal();
            String registrationId = oauthToken.getAuthorizedClientRegistrationId();
            String subject = extractSubject(oauth2User, registrationId);

            user = identityRepository.findByProviderAndSubject(registrationId, subject)
                    .map(identity -> userRepository.findById(identity.getUserId())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found")))
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        } else if (authentication.getPrincipal() instanceof UUID userId) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        } else {
            throw new ResourceNotFoundException("User not found");
        }

        if (user.isDeleted()) {
            throw new ResourceNotFoundException("User not found");
        }
        if (!user.isClaimed()) {
            throw new ResourceNotFoundException("User not found");
        }

        return user;
    }

    private String extractSubject(OAuth2User oauth2User, String registrationId) {
        if ("github".equals(registrationId)) {
            Object idAttr = oauth2User.getAttribute("id");
            return idAttr != null ? idAttr.toString() : oauth2User.getName();
        }
        return oauth2User.getName();
    }
}
