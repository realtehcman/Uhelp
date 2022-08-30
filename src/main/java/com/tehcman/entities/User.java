package com.tehcman.entities;

import javax.persistence.*;

@Entity
public class User {

    //fill the following fields from the user's message
    @Id
    private final Long id;
    private final String tgUsername;


    private String name;

    @Transient
    private Phase phase;

    private String phoneNumber;
    private String email;
    private String social;


    private String age;


    //sprint 2:
    @Enumerated(EnumType.STRING)
    private Status status;
    private Character sex;
    private String city;

    private String country;
    private Integer amountOfPeople;
    private String date;
    private String additional;


    public User(Long id, String tgUsername, String name, Phase phase) {
        this.id = id;
        this.tgUsername = tgUsername;
        this.name = name;
        this.phase = phase;
    }

    public Long getId() {
        return id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAmountOfPeople(Integer amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    @Override
    public String toString() {
        return "User{\n" +
                "id=" + id + '\n' +
                "tgUsername=" + tgUsername + '\n' +
                "name=" + name + '\n' +
                "phase=" + phase + '\n' +
                "phoneNumber=" + phoneNumber + '\n' +
                "email=" + email + '\n' +
                "social=" + social + '\n' +
                "age=" + age + '\n' +
                "status=" + status + '\n' +
                "sex=" + sex + '\n' +
                "city=" + city + '\n' +
                "country=" + country + '\n' +
                "amountOfPeople=" + amountOfPeople + '\n' +
                "date=" + date + '\n' +
                "additional=" + additional + '\n' +
                '}';
    }

}
