package com.peters.myapp.dtos;

import com.peters.myapp.models.Appointment;

import java.util.List;

public record AppointmentResponse(List<Appointment> appointments) {
}
