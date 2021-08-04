package com.whatever.beerfinder.services;

import com.whatever.beerfinder.builders.BreweryDistanceMatrixBuilder;
import com.whatever.beerfinder.dao.BeerDataDao;
import com.whatever.beerfinder.entities.Brewery;
import com.whatever.beerfinder.entities.Geocode;
import com.whatever.beerfinder.models.BreweryLocation;
import com.whatever.beerfinder.models.BreweryPath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BreweryPathFinderService {

    private static final Double MAX_KM = 20000.0;
    private final BeerDataDao beerDataDao;
    private final BreweryDistanceMatrixBuilder breweryDistanceMatrixBuilder = new BreweryDistanceMatrixBuilder();
    private HashMap<BreweryLocation, HashMap<BreweryLocation, Double>> distancesBetweenBreweries = new HashMap<>();

    public List<BreweryLocation> findBestPathFromStartingPoint(BreweryLocation startingPoint) {
        initialize(startingPoint);
        BreweryPath path = getPath(startingPoint, startingPoint, MAX_KM);
        return unwrapPath(path);
    }

    private BreweryPath getPath(BreweryLocation from, BreweryLocation finalDestination, Double distanceLeft
    ) {
        HashMap<BreweryLocation, Double> neighbouringBreweries = distancesBetweenBreweries.get(from);
        Integer maxBreweriesVisited = -1;
        BreweryPath longestBreweryPath = null;
        markNodeAsVisited(from);

        if (distanceLeft <= 0) {
            return null;
        }

        if (nodeIsTheStartingNode(from, finalDestination) && !Objects.equals(distanceLeft, MAX_KM)) {
            return new BreweryPath(finalDestination, null, 0);
        }

        for (Map.Entry<BreweryLocation, Double> neighbourBreweryAndDistance : neighbouringBreweries.entrySet()) {
            if (neighbourBreweryAndDistance.getValue() > 0) {
                Double distanceToNextBrewery = neighbourBreweryAndDistance.getValue();
                BreweryLocation nextBrewery = neighbourBreweryAndDistance.getKey();
                if (!nextBrewery.isTraversed() || nodeIsTheStartingNode(nextBrewery, finalDestination)) {
                    Double newDistance = distanceLeft - distanceToNextBrewery;

                    BreweryPath breweryPath = getPath(nextBrewery, finalDestination, newDistance);

                    if (breweryPath != null && breweryPath.getSteps() > maxBreweriesVisited) {
                        breweryPath.setDistanceFromPrevious(distanceToNextBrewery);
                        maxBreweriesVisited = breweryPath.getSteps();
                        longestBreweryPath = breweryPath;
                    }
                }
            }

        }
        unmarkNodeAsVisited(from);

        if (longestBreweryPath == null) {
            return null;
        }
        return new BreweryPath(from, longestBreweryPath,longestBreweryPath.getSteps() + 1);
    }

    private void markNodeAsVisited(BreweryLocation node) {
        node.setTraversed(true);
    }

    private void unmarkNodeAsVisited(BreweryLocation node) {
        node.setTraversed(false);
    }

    private boolean nodeIsTheStartingNode(BreweryLocation from, BreweryLocation finalDestination) {
        return Objects.equals(from.getBreweryId(), finalDestination.getBreweryId());
    }

    private void initialize(BreweryLocation startingPoint) {
        if (this.distancesBetweenBreweries.size() < 1) {
            List<Brewery> breweryList = beerDataDao.listBreweries();
            List<Geocode> geocodeList = beerDataDao.listGeocodes();
            List<BreweryLocation> breweryLocationList = new ArrayList<>();

            for (Brewery brewery : breweryList) {
                Geocode geocodeOfBrewery = findGeocodeOfBrewery(brewery, geocodeList)
                        .orElseThrow(() ->
                                new IllegalStateException("Can't find geocode of brewery with id " + brewery.getId()));

                BreweryLocation breweryLocation = new BreweryLocation(brewery.getId(), geocodeOfBrewery.getLatitude(),
                        geocodeOfBrewery.getLongitude(), brewery.getName());

                breweryLocationList.add(breweryLocation);
            }

            breweryLocationList.add(startingPoint);

            this.distancesBetweenBreweries = breweryDistanceMatrixBuilder.buildDistancesBetweenBreweries(breweryLocationList);
        }
    }

    private Optional<Geocode> findGeocodeOfBrewery(Brewery brewery, List<Geocode> geocodeList) {
        return geocodeList.stream().filter(geocode -> Objects.equals(geocode.getBreweryId(), brewery.getId())).findFirst();
    }

    private List<BreweryLocation> unwrapPath(BreweryPath path) {
        if (path == null) {
            return new ArrayList<>();
        }
        List<BreweryLocation> breweryLocationList = new ArrayList<>();

        double distanceFromPrevious = path.getDistanceFromPrevious() == null ? 0 : path.getDistanceFromPrevious();
        BreweryLocation brewery = new BreweryLocation(path.getBrewery().getBreweryId(), path.getBrewery().getLatitude(),
                path.getBrewery().getLongitude(), path.getBrewery().getName());
        brewery.setDistanceFromOptimalBrewery(distanceFromPrevious);
        breweryLocationList.add(brewery);
        breweryLocationList.addAll(unwrapPath(path.getNextNode()));
        return breweryLocationList;
    }
}
