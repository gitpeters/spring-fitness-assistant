package com.peters.myapp.dtos;

import com.peters.myapp.models.Business;
import com.peters.myapp.models.FitnessClass;

import java.io.Serializable;

public record AddedClassResponse(FitnessClass fitnessClass, Business business) implements Serializable {
}
