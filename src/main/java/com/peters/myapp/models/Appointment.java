package com.peters.myapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
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
