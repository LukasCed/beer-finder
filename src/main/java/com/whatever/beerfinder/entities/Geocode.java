package com.whatever.beerfinder.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Geocode {
    private final int id;
    private final int breweryId;
    private final double latitude;
    private final double longitude;
    private final AccuracyEnum accuracyEnum;
}
