package com.peters.myapp.dtos;

import com.peters.myapp.models.Business;
import com.peters.myapp.models.Facility;

public record AddFacilityResponse(Facility facility, Business business) {
}
