package com.barberos.auth.internal;

import com.barberos.identity.UserBootstrapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * Handles GitHub OAuth2 login. Subject is the stable numeric GitHub ID.
 * D16: Uses identities join table via UserBootstrapService.
 * D20: NFKC email normalization via UserBootstrapService.normalizeEmail().
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger log = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    private final UserBootstrapService bootstrapService;

    public CustomOAuth2UserService(UserBootstrapService bootstrapService) {
        this.bootstrapService = bootstrapService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Object idAttr = oauth2User.getAttribute("id");
        String subject = idAttr != null ? idAttr.toString() : oauth2User.getName();

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        if (email == null || email.isBlank()) {
            email = subject + "@github.auth.barberos.com";
        }
        email = UserBootstrapService.normalizeEmail(email);

        if (name == null || name.isBlank()) {
            String login = oauth2User.getAttribute("login");
            name = login != null ? login : "User";
        }

        bootstrapService.provisionOAuth2(email, registrationId, subject, name);

        return oauth2User;
    }
}
