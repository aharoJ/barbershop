package io.aharoj.barbershop_backend.modules.appointment.service;

import java.util.List;

import io.aharoj.barbershop_backend.modules.appointment.dto.request.AppointmentRequest;
import io.aharoj.barbershop_backend.modules.appointment.dto.response.AppointmentResponse;

public interface AppointmentService {

  /**
   * Customer books a new appointment (starts in PENDING state).
   *
   * @param customerUserId the ID of the customer creating the appointment
   * @param request        the details of the appointment to be created
   * @return an AppointmentResponse containing the details of the newly created
   *         appointment
   * @note for client
   */
  AppointmentResponse createAppointment(Long customerUserId, AppointmentRequest request);

  /**
   * Barber (or system) confirms an appointment, transitioning it from PENDING to
   * CONFIRMED.
   *
   * @param appointmentId the ID of the appointment to be confirmed
   * @param barberUserId  the ID of the barber confirming the appointment
   * @return an AppointmentResponse containing the updated appointment details
   * @note for client
   */
  AppointmentResponse confirmAppointment(Long appointmentId, Long barberUserId);

  /**
   * Barber or customer cancels an appointment.
   *
   * @param appointmentId   the ID of the appointment to be canceled
   * @param requesterUserId the ID of the user (barber or customer) requesting the
   *                        cancellation
   * @return an AppointmentResponse containing the updated appointment details
   *         after cancellation
   * @note for client
   */
  AppointmentResponse cancelAppointment(Long appointmentId, Long requesterUserId);

  /**
   * Barber marks an appointment as COMPLETED once the service is done.
   *
   * @param appointmentId the ID of the appointment to be marked as completed
   * @param barberUserId  the ID of the barber completing the appointment
   * @return an AppointmentResponse containing the updated appointment details
   *         after completion
   * @note for client
   */
  AppointmentResponse completeAppointment(Long appointmentId, Long barberUserId);

  /**
   * Retrieve a single appointment by its ID.
   *
   * @param appointmentId   the ID of the appointment to be retrieved
   * @param requesterUserId the ID of the user requesting the appointment details
   * @return an AppointmentResponse containing the details of the requested
   *         appointment
   * @note for client
   */
  AppointmentResponse getAppointment(Long appointmentId, Long requesterUserId);

  /**
   * Retrieve all appointments for a specific user (could be a customer or a
   * barber).
   *
   * @param userId the ID of the user whose appointments are to be retrieved
   * @return a list of AppointmentResponse objects containing the details of the
   *         user's appointments
   * @note for client
   */
  List<AppointmentResponse> getAppointmentsForUser(Long userId);
}
