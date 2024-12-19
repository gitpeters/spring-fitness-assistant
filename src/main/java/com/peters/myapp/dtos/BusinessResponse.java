package com.peters.myapp.dtos;

import com.peters.myapp.models.Business;

import java.io.Serializable;
import java.util.List;

public record BusinessResponse(List<Business> businesses) implements Serializable {
}
