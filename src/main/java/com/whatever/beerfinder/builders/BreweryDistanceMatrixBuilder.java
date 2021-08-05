package com.whatever.beerfinder.builders;

import com.whatever.beerfinder.models.BreweryNode;
import com.whatever.beerfinder.models.BreweryTreeMap;
import com.whatever.beerfinder.utils.MathUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Optimizations:
 * 1) Not including neighbours if they are too far away either from home or the distance between one node and another
 * is more than max allowed
 * 2) Neighbours are sorted from lowest distance to highest
 * 3) Home node, if it's a neighbour, is always the first neighbour to visit - this way nodes try to return home first
 * before straying away
 */
public class BreweryDistanceMatrixBuilder {

    public HashMap<BreweryNode, BreweryTreeMap> buildDistancesBetweenBreweries(
            List<BreweryNode> breweryList, Double maxDistance, BreweryNode homeNode) {
        HashMap<BreweryNode, BreweryTreeMap> distancesBetweenBreweries = new HashMap<>();

        breweryList.forEach(brewery1 -> {
            distancesBetweenBreweries.put(brewery1, new BreweryTreeMap());
            BreweryTreeMap breweryDistanceHashMap = distancesBetweenBreweries.get(brewery1);

            breweryList.forEach(brewery2 -> {
                double distance = MathUtils.calculateHaversine(brewery1.getLatitude(), brewery1.getLongitude(),
                        brewery2.getLatitude(), brewery2.getLongitude());
                double distanceToHome = MathUtils.calculateHaversine(homeNode.getLatitude(), homeNode.getLongitude(),
                        brewery1.getLatitude(), brewery1.getLongitude());
                double distanceFromNeighbourToHome = MathUtils.calculateHaversine(homeNode.getLatitude(), homeNode.getLongitude(),
                        brewery2.getLatitude(), brewery2.getLongitude());
                if (distance <= maxDistance / 2 && distanceFromNeighbourToHome <= maxDistance / 2 &&
                        distance + distanceToHome + distanceFromNeighbourToHome <= maxDistance) {
                    breweryDistanceHashMap.put(distance, brewery2);
                }
            });

            breweryDistanceHashMap.rebalance();
        });

        return distancesBetweenBreweries;
    }
}
