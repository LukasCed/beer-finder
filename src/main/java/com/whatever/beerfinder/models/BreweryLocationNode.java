package com.whatever.beerfinder.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BreweryLocationNode {
    private final int breweryId;
    private final double latitude;
    private final double longitude;
    private final String name;
    private double distanceFromOptimalBrewery;
    private boolean traversed;

    @Override
    public String toString() {
        return (breweryId == -1 ? "" : "[" + breweryId + "] ") + name + " " + latitude + " " + longitude;
    }

}
