package com.whatever.beerfinder.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Geocode {
    @Id
    private int id;
    @Column
    private int breweryId;
    @Column
    private double latitude;
    @Column
    private double longitude;

    private Geocode() {
        
    }
}
