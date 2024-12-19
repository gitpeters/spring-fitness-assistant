package com.peters.myapp.dtos;

import com.peters.myapp.models.Facility;

import java.util.List;

public record FacilityResponse(List<Facility> facilities) {
}
