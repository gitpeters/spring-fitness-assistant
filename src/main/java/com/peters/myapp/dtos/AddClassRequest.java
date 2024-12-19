package com.peters.myapp.dtos;

import com.peters.myapp.models.FitnessClass;

import java.io.Serializable;

public record AddClassRequest(FitnessClass fitnessClass, long businessId, String category) implements Serializable {
}
