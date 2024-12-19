package com.peters.myapp.dtos;

import com.peters.myapp.models.Business;

import java.io.Serializable;

public record AddBusinessRequest(
        String name,
        String address,
        String phone,
        String email,
        String city,
        String state,
        String country,
        String contactPerson) implements Serializable {
}
