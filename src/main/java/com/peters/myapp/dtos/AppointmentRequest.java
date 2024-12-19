package com.peters.myapp.dtos;

import com.peters.myapp.models.Appointment;
import com.peters.myapp.models.FitnessClass;

public record AppointmentRequest(Appointment appointment, long businessId) {
}
