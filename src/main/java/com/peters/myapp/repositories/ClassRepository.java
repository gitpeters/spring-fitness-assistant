package com.peters.myapp.repositories;

import com.peters.myapp.models.FitnessClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ClassRepository extends JpaRepository<FitnessClass, Long> {
    List<FitnessClass> findAllByBusinessId(Long businessId);
    List<FitnessClass> findAllByStartDateBetween(LocalDate startDate, LocalDate endDate);
    List<FitnessClass> findAllByPriceBetween(BigDecimal lower, BigDecimal higher);
}
