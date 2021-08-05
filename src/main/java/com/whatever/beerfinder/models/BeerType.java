package com.whatever.beerfinder.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BeerType {
    private final String typeName;

    @Override
    public String toString() {
        return typeName;
    }
}
