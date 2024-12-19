package com.peters.myapp.repositories;

import com.peters.myapp.models.Appointment;
import com.peters.myapp.models.FitnessClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByBusinessId(long id);
    List<Appointment> findAllByPriceBetween(BigDecimal lower, BigDecimal higher);
}
