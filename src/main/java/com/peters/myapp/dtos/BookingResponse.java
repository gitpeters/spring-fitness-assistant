package com.peters.myapp.dtos;

import com.peters.myapp.models.Booking;

import java.io.Serializable;
import java.util.List;

public record BookingResponse(List<Booking> bookings) implements Serializable {
}
