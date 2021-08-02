package com.whatever.beerfinder.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Brewery {
    private final int id;
    private final String name;
    private final String address1;
    private final String address2;
    private final String city;
    private final String state;
    private final String code;
    private final String country;
    private final String phone;
    private final String website;
    private final String filepath;
    private final String descript;
    private final int addUser;
    private final Timestamp lastMod;
}
