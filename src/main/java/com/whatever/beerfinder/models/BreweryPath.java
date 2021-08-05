package com.whatever.beerfinder.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BreweryPath {
    private final BreweryNode brewery;
    private final BreweryPath nextNode;
    private final Integer steps;
    private Double distanceFromPrevious;
}
