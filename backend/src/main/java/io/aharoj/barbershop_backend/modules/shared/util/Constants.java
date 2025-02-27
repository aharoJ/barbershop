package io.aharoj.barbershop_backend.modules.shared.util;

import io.aharoj.barbershop_backend.modules.appointment.model.enums.AppointmentStatus;

/**
 * MUST DELETE THIS AT ONE POINT
 * 
 * @note annoying constans attached to under developed modules
 */
public class Constants {

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

  // Appointment Status --> not sure if we linked correct
  public static final AppointmentStatus PENDING = AppointmentStatus.PENDING;
  public static final AppointmentStatus CONFIRMED = AppointmentStatus.CONFIRMED;
  public static final AppointmentStatus COMPLETED = AppointmentStatus.COMPLETED;

}
