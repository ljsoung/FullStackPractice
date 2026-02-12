package com.packt.cardatabase.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ownerId;

    private String firstName;

    private String lastName;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "car_owner", joinColumns = {
            @JoinColumn(name = "ownerid")},
    inverseJoinColumns = {
            @JoinColumn(name = "id")})
    private Set<Car> cars = new HashSet<Car>();

    public Owner() {
    }

    public Owner(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
