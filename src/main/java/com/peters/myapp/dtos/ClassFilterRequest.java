package com.peters.myapp.dtos;

import java.time.LocalDate;

public record ClassFilterRequest(LocalDate startDate, LocalDate endDate) {
}
