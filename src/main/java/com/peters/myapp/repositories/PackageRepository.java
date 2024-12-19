package com.peters.myapp.repositories;

import com.peters.myapp.models.FitnessClass;
import com.peters.myapp.models.Package;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Long> {
    List<Package> findAllByBusinessId(Long businessId);
    List<Package> findAllByPriceBetween(BigDecimal lower, BigDecimal higher);
}
