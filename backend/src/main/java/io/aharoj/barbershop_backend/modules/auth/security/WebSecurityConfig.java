package io.aharoj.barbershop_backend.modules.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        // .addFilterBefore(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class) -- THIS BREAKS DB 

        // exceptionHandlingCustomizer
        .exceptionHandling(e -> {
          e.authenticationEntryPoint(unauthorizedHandler);
          e.accessDeniedHandler(accessDeniedHandler);
        })

        // sessionManagementCustomizer
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // authoriezed request
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()

            // Permit H2 Console access
            .requestMatchers(PathRequest.toH2Console()).permitAll()

            // Public endpoints: signup, signin
            .requestMatchers("/api/test/auth/all").permitAll()
            .requestMatchers("/api/test/auth/user").hasAnyRole("CUSTOMER", "BARBER", "OWNER")
            .requestMatchers("/api/test/auth/owner").hasRole("OWNER")
            // .requestMatchers("/api/test/auth/admin").hasRole("ADMIN") // removed to
            // reduce complexity

            // OWNER PROFILES
            .requestMatchers("/api/owners/**").hasRole("OWNER")

            // SHOP && SEATS
            .requestMatchers("/api/shops/**").hasRole("OWNER")

            // BARBER
            .requestMatchers("/api/barbers/**").hasRole("BARBER")

            // CUSTOMER
            .requestMatchers("/api/customers/**").permitAll()

            .anyRequest().authenticated())

        .headers(headers -> headers.frameOptions().sameOrigin()) // Allow H2 Console frames
        .authenticationProvider(authenticationProvider());

    httpSecurity.authenticationProvider(authenticationProvider());
    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

}
