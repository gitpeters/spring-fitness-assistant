package com.peters.myapp.dtos;

import com.peters.myapp.models.Appointment;
import com.peters.myapp.models.Business;

import java.io.Serializable;

public record AddAppointmentResponse(Appointment appointment, Business business) implements Serializable {
}
