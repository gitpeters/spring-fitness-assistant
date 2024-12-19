package com.peters.myapp.dtos;

import com.peters.myapp.models.FitnessClass;

public record ClassRequest(FitnessClass fitnessClass, long businessId) {
}
