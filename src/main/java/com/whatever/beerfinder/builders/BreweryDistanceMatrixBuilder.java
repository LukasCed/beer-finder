package com.whatever.beerfinder.builders;

import com.whatever.beerfinder.models.BreweryLocation;
import com.whatever.beerfinder.utils.MathUtils;

import java.util.HashMap;
import java.util.List;

public class BreweryDistanceMatrixBuilder {

    public HashMap<BreweryLocation, HashMap<BreweryLocation, Double>> buildDistancesBetweenBreweries(
            List<BreweryLocation> breweryList) {
        HashMap<BreweryLocation, HashMap<BreweryLocation, Double>> distancesBetweenBreweries = new HashMap<>();

        breweryList.forEach(brewery1 -> {
            distancesBetweenBreweries.put(brewery1, new HashMap<>());
            HashMap<BreweryLocation, Double> breweryDistanceHashMap = distancesBetweenBreweries.get(brewery1);

            breweryList.forEach(brewery2 -> {
                breweryDistanceHashMap.put(brewery2,
                        MathUtils.calculateHaversine(brewery1.getLatitude(), brewery1.getLongitude(),
                                brewery2.getLatitude(), brewery2.getLongitude()));
            });
        });

        return distancesBetweenBreweries;
    }
}
