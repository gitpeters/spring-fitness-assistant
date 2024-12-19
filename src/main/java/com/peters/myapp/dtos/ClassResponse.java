package com.peters.myapp.dtos;

import com.peters.myapp.models.FitnessClass;

import java.util.List;

public record ClassResponse(List<FitnessClass> fitnessClasses) {
}
