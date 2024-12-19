package com.peters.myapp.dtos;

import com.peters.myapp.models.Business;

import java.io.Serializable;

public record AddBusinessResponse(Business business) implements Serializable {
}
