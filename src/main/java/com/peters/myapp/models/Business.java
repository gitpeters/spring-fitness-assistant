package com.peters.myapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Getter @Setter
@Table(name = "businesses")
@AllArgsConstructor
@NoArgsConstructor
public class Business extends BaseEntity{
    private String name;
    private String address;
    private String phone;
    private String email;
    private String city;
    private String state;
    private String country;
    private String contactPerson;
}
