package com.peters.myapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "packages")
public class Package extends BaseEntity{
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private String validityPeriod;
    private int validityInterval;
    private BigDecimal price;
    private String benefits;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PACKAGE_OWNER"))
    private Business business;
}
