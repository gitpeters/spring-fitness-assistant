package com.peters.myapp.dtos;

import com.peters.myapp.models.Facility;

public record FacilityRequest(Facility facility, long businessId) {
}
