package com.peters.myapp.dtos;

import com.peters.myapp.models.Appointment;
import com.peters.myapp.models.Booking;
import com.peters.myapp.models.Business;

import java.io.Serializable;

public record AddBookingResponse(Booking booking) implements Serializable {
}
