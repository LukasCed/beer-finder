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
public class Beer {
    private final int id;
    private final int breweryId;
    private final String name;
    private final int catId;
    private final int styleId;
    private final double abv;
    private final double ibu;
    private final double srm;
    private final int upc;
    private final String filepath;
    private final String descript;
    private final int addUser;
    private final Timestamp lastMod;
}
