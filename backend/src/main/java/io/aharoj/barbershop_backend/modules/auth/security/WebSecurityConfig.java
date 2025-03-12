package io.aharoj.barbershop_backend.modules.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.aharoj.barbershop_backend.modules.auth.security.util.CustomAccessDeniedHandler;
import io.aharoj.barbershop_backend.modules.auth.security.util.JwtAuthenticationEntryPoint;
import io.aharoj.barbershop_backend.modules.auth.security.util.JwtRequestFilter;
import io.aharoj.barbershop_backend.modules.auth.serviceImpl.UserDetailsServiceImpl;

// h2 stuff
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
  /**
   * @deny unauthorized request
   */
  @Autowired
  private JwtAuthenticationEntryPoint unauthorizedHandler;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    var authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity, CustomAccessDeniedHandler accessDeniedHandler)
      throws Exception {
    httpSecurity
        .cors(Customizer.withDefaults()) // FRONTEND --> newest modification for cors <-> frontend access to backend
        .csrf(csrf -> csrf.disable()) // tied to top
        // .addFilterBefore(new JwtRequestFilter(),
        // UsernamePasswordAuthenticationFilter.class) -- THIS BREAKS DB

        // exceptionHandlingCustomizer
        .exceptionHandling(e -> {
          e.authenticationEntryPoint(unauthorizedHandler);
          e.accessDeniedHandler(accessDeniedHandler);
        })

        // sessionManagementCustomizer
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // authoriezed request
        .authorizeHttpRequests(auth -> auth

            // Authentication endpoints
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/users/**").permitAll()

            // H2 console
            .requestMatchers(PathRequest.toH2Console()).permitAll()

            // Some test endpoints
            .requestMatchers("/api/test/auth/all").permitAll()
            .requestMatchers("/api/test/auth/user").hasAnyRole("CUSTOMER", "BARBER", "OWNER")
            .requestMatchers("/api/test/auth/owner").hasRole("OWNER")

            // Owner profiles
            .requestMatchers("/api/owners/**").hasRole("OWNER")

            // Barber profiles
            .requestMatchers("/api/barbers/**").hasRole("BARBER")

            // Customer profiles (example: maybe you let them see themselves)
            .requestMatchers("/api/customers/**").hasRole("CUSTOMER")

            // Shop endpoints:
            // 1) Everyone can GET /api/shops, GET /api/shops/{id}
            .requestMatchers(HttpMethod.GET, "/api/shops").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/shops/*").permitAll()

            // 2) Only owners can create a shop (POST /api/shops/owner/create, or however
            // you named it)
            .requestMatchers(HttpMethod.POST, "/api/shops/owner/create").hasRole("OWNER")

            // 3) If you want owners to do other CRUD on shops:
            .requestMatchers(HttpMethod.PUT, "/api/shops/*").hasRole("OWNER")
            .requestMatchers(HttpMethod.DELETE, "/api/shops/*").hasRole("OWNER")

            // 4) Seats:
            .requestMatchers(HttpMethod.POST, "/api/shops/*/seats/**").hasRole("OWNER")

            // 5) Applications:
            .requestMatchers(HttpMethod.POST, "/api/shops/*/applications").hasRole("BARBER")
            .requestMatchers(HttpMethod.GET, "/api/shops/*/applications").hasRole("OWNER")
            .requestMatchers(HttpMethod.POST, "/api/shops/*/applications/*/approve").hasRole("OWNER")
            .requestMatchers(HttpMethod.POST, "/api/shops/*/applications/*/reject").hasRole("OWNER")

            // 5) Images:
            .requestMatchers( "/api/images/owners/**").hasRole("OWNER")
            .requestMatchers(HttpMethod.POST, "/api/images/barbers/**").hasRole("BARBER")
            .requestMatchers(HttpMethod.POST, "/api/images/customers/**").hasRole("CUSTOMER")
            // .requestMatchers(HttpMethod.POST, "/api/images/shops/**").hasRole("OWNER") // -- this is buggy?
            .requestMatchers(HttpMethod.GET, "/api/images/files/**").permitAll()

            // Actuator, etc.
            .requestMatchers("/actuator/**").permitAll()

            // fallback
            .anyRequest()
            .authenticated())

        .headers(headers -> headers.frameOptions().sameOrigin()) // Allow H2 Console frames
        .authenticationProvider(authenticationProvider());

    httpSecurity.authenticationProvider(authenticationProvider());
    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }
}
