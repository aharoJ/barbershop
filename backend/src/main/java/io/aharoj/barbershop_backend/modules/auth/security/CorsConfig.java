package io.aharoj.barbershop_backend.modules.auth.security;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**") // apply to all endpoints
            .allowedOrigins(
                "http://localhost:3000",
                // "http://localhost:3000/signup", // -- TESTING
                "http://localhost:5173",
                "http://127.0.0.1:5173" 
        )
            .allowedMethods("*") // GET, POST, PUT, DELETE, etc.
            .allowedHeaders("*")
            .allowCredentials(true);
      }
    };
  }
}
