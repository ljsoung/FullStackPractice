package com.packt.cardatabase.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Car {

    @Id // 기본키 정의
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동 ID 생성
    private Long id;

    private String brand;

    private String model;

    private String color;

    private String registrationNumber; // 등록번호

    private int modelYear;

    private int price;

    @ManyToOne(fetch = FetchType.LAZY) // 차는 다양하나 주인은 하나
    @JoinColumn(name = "owner")
    private Owner owner;

    @ManyToMany(mappedBy = "cars")
    private Set<Owner> owners = new HashSet<Owner>();

    public Car() {}

    public Car(String brand, String model, String color, String registrationNumber, int modelYear, int price, Owner owner) {
        super();
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.registrationNumber = registrationNumber;
        this.modelYear = modelYear;
        this.price = price;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }
}
