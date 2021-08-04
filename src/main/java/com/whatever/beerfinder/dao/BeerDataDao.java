package com.whatever.beerfinder.dao;

import com.whatever.beerfinder.entities.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BeerDataDao {

    public List<Geocode> listGeocodes() {
        return new ArrayList<>();
    }

    public List<Beer> listBeers(List<Integer> breweryIds) {
        return new ArrayList<>();
    }

    public List<Brewery> listBreweries() {
        return new ArrayList<>();
    }

}
