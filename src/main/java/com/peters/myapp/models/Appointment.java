package com.peters.myapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
public class Appointment extends BaseEntity {
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private AppointmentType type;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_id", nullable = false, foreignKey = @ForeignKey(name = "FK_APPOINTMENT_OWNER"))
    private Business business;
}
