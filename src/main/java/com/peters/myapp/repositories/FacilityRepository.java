package com.peters.myapp.repositories;

import com.peters.myapp.models.Facility;
import com.peters.myapp.models.FitnessClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findAllByBusinessId(Long businessId);
    List<Facility> findAllByPriceBetween(BigDecimal lower, BigDecimal higher);
}
