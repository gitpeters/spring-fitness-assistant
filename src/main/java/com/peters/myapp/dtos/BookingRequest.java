package com.peters.myapp.dtos;

import com.peters.myapp.models.Booking;

import java.io.Serializable;

public record BookingRequest(long userId) implements Serializable {
}
