package com.barberos.auth.internal;

import com.barberos.identity.UserBootstrapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

/**
 * Handles Google OIDC login. Uses stable `sub` claim.
 * D16: Uses identities join table via UserBootstrapService.
 * D20: NFKC email normalization via UserBootstrapService.normalizeEmail().
 */
@Service
public class CustomOidcUserService extends OidcUserService {

    private static final Logger log = LoggerFactory.getLogger(CustomOidcUserService.class);

    private final UserBootstrapService bootstrapService;

    public CustomOidcUserService(UserBootstrapService bootstrapService) {
        this.bootstrapService = bootstrapService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String subject = oidcUser.getSubject();
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();

        if (email == null || email.isBlank()) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("missing_email", "Email is required for registration", null));
        }
        email = UserBootstrapService.normalizeEmail(email);

        if (name == null || name.isBlank()) {
            name = email.split("@")[0];
        }

        bootstrapService.provisionOAuth2(email, registrationId, subject, name);

        return oidcUser;
    }
}
