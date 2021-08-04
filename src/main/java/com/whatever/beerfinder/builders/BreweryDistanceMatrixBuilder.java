package com.whatever.beerfinder.builders;

import com.whatever.beerfinder.models.BreweryLocationNode;
import com.whatever.beerfinder.utils.MathUtils;

import java.util.HashMap;
import java.util.List;

public class BreweryDistanceMatrixBuilder {

    public HashMap<BreweryLocationNode, HashMap<BreweryLocationNode, Double>> buildDistancesBetweenBreweries(
            List<BreweryLocationNode> breweryList, Double maxDistance) {
        HashMap<BreweryLocationNode, HashMap<BreweryLocationNode, Double>> distancesBetweenBreweries = new HashMap<>();

        breweryList.forEach(brewery1 -> {
            distancesBetweenBreweries.put(brewery1, new HashMap<>());
            HashMap<BreweryLocationNode, Double> breweryDistanceHashMap = distancesBetweenBreweries.get(brewery1);

            breweryList.forEach(brewery2 -> {
                double distance = MathUtils.calculateHaversine(brewery1.getLatitude(), brewery1.getLongitude(),
                        brewery2.getLatitude(), brewery2.getLongitude());
                if (distance <= maxDistance) {
                    breweryDistanceHashMap.put(brewery2, distance);
                }
            });
        });

        return distancesBetweenBreweries;
    }
}
