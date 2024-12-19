package com.peters.myapp.dtos;

import com.peters.myapp.models.Business;

import java.io.Serializable;


public record BusinessRequest(Business business) implements Serializable {
}
