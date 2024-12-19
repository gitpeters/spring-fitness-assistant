package com.peters.myapp.dtos;

import java.io.Serializable;

public record AddBookingRequest(long userId, long classId, long businessId) implements Serializable {
}
