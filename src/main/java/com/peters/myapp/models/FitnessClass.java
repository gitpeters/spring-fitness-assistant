package com.peters.myapp.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "classes")
@Builder
public class FitnessClass extends BaseEntity{
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private BigDecimal price;
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @Column(name = "start_time")
    @DateTimeFormat(pattern = "hh:mm:ss")
    private LocalTime startTime;
    @Column(name = "end_time")
    @DateTimeFormat(pattern = "hh:mm:ss")
    private LocalTime endTime;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_id", nullable = false, foreignKey = @ForeignKey(name = "FK_CLASS_OWNER"))
    private Business business;
}
