package com.peters.myapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
public class Booking extends BaseEntity{
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", foreignKey = @ForeignKey(name = "FK_BOOKED_CLASS"))
    private FitnessClass fitnessClass;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_id", foreignKey = @ForeignKey(name = "FK_BOOKED_APPOINTMENT"))
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "facility_id", foreignKey = @ForeignKey(name = "FK_BOOKED_FACILITY"))
    private Facility facility;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "package_id", foreignKey = @ForeignKey(name = "FK_BOOKED_PACKAGE"))
    private Package _package;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_BOOKING_USER"))
    @JsonBackReference
    private User user;
}
