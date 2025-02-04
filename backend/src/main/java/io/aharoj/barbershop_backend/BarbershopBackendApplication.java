package io.aharoj.barbershop_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BarbershopBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(BarbershopBackendApplication.class, args);
  }
}