package com.peters.myapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "facilities")
public class Facility extends BaseEntity{
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private int capacity;
    private BigDecimal price;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_id", nullable = false, foreignKey = @ForeignKey(name = "FK_FACILITY_OWNER"))
    private Business business;
}
