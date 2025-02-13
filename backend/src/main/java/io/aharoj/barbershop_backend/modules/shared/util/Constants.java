package io.aharoj.barbershop_backend.modules.shared.util;

import io.aharoj.barbershop_backend.modules.appointment.model.enums.AppointmentStatus;
import io.aharoj.barbershop_backend.modules.auth.model.enums.RoleType;

/**
 * Create a utility class to store application-wide constants.
 */

public class Constants {

  // Role Constants
  public static final RoleType ROLE_CUSTOMER = RoleType.ROLE_CUSTOMER;
  public static final RoleType ROLE_BARBER = RoleType.ROLE_BARBER;
  public static final RoleType ROLE_OWNER = RoleType.ROLE_OWNER;
  // public static final RoleType ROLE_ADMIN = RoleType.ROLE_OWNER; // REMOVED FOR NOW ?


  // Error Messages
  public static final String USER_NOT_FOUND = "User not found";
  public static final String SHOP_NOT_FOUND = "Shop not found";
  public static final String APPOINTMENT_NOT_FOUND = "Appointment not found";
  public static final String REVIEW_NOT_FOUND = "Review not found";
  public static final String PROMOTION_NOT_FOUND = "Promotion not found";
  public static final String PAYMENT_NOT_FOUND = "Payment not found";
  public static final String IMAGE_NOT_FOUND = "Image not found";

  // Success Messages
  public static final String USER_CREATED_SUCCESSFULLY = "User created successfully";
  public static final String APPOINTMENT_BOOKED_SUCCESSFULLY = "Appointment booked successfully";
  public static final String REVIEW_SUBMITTED_SUCCESSFULLY = "Review submitted successfully";
  public static final String PAYMENT_PROCESSED_SUCCESSFULLY = "Payment processed successfully";

  // API Base Path
  public static final String API_BASE_PATH = "/api";

  // Date Formats
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  // Other Constants
  // ...

  // Appointment Status --> not sure if we linked correct
  public static final AppointmentStatus PENDING = AppointmentStatus.PENDING;
  public static final AppointmentStatus  CONFIRMED= AppointmentStatus.CONFIRMED;
  public static final AppointmentStatus COMPLETED = AppointmentStatus.COMPLETED;

  private Constants() {
    // Private constructor to prevent instantiation
  }
}
