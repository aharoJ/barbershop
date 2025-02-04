package io.aharoj.barbershop_backend.modules.appointment.model.enums;

public enum AppointmentStatus {
  PENDING, // Customer created, awaiting confirmation
  CONFIRMED, // Barber confirmed
  COMPLETED, // Service done
  CANCELED // Either barber or customer canceled
}
