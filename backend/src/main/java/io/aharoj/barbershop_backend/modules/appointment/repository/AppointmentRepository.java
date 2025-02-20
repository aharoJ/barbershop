package io.aharoj.barbershop_backend.modules.appointment.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.aharoj.barbershop_backend.modules.appointment.model.entity.Appointment;
import io.aharoj.barbershop_backend.modules.barber.model.entity.Barber;
import io.aharoj.barbershop_backend.modules.customer.model.entity.Customer;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

  // Find appointments for a specific barber in a time range
  List<Appointment> findByBarberProfileAndAppointmentTimeBetween(
      Barber barberProfile,
      LocalDateTime start,
      LocalDateTime end);

  // Find appointments for a specific customer
  List<Appointment> findByCustomerProfile(Customer customerProfile);

}
