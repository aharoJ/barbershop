package io.aharoj.barbershop_backend.modules.auth.model.enums;

public enum RoleType {
  ROLE_OWNER,
  ROLE_BARBER,
  ROLE_CUSTOMER;
  // OWNER, -- SPRING SECURITY LOOKS FOR "ROLE_blah"
  // WRITE_PRIVILEGE,  -- POTENTIAL FUTURE IMPL
  // READ_PRIVILEGE, -- POTENTIAL FUTURE IMPL
}
