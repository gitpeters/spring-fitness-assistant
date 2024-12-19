package com.peters.myapp.repositories;

import com.peters.myapp.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    boolean existsByName(String trim);
}
