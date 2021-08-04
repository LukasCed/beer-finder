package com.whatever.beerfinder.services;

import com.whatever.beerfinder.builders.BreweryDistanceMatrixBuilder;
import com.whatever.beerfinder.config.PathFinderConfig;
import com.whatever.beerfinder.dao.interfaces.BreweryDao;
import com.whatever.beerfinder.dao.interfaces.GeocodeDao;
import com.whatever.beerfinder.entities.Brewery;
import com.whatever.beerfinder.entities.Geocode;
import com.whatever.beerfinder.models.BreweryLocationNode;
import com.whatever.beerfinder.models.BreweryPath;
import com.whatever.beerfinder.utils.MathUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BreweryPathFinderServiceImpl {

    private final PathFinderConfig pathFinderConfig;
    private final BreweryDao breweryDao;
    private final GeocodeDao geocodeDao;
    private final BreweryDistanceMatrixBuilder breweryDistanceMatrixBuilder = new BreweryDistanceMatrixBuilder();
    private HashMap<BreweryLocationNode, HashMap<BreweryLocationNode, Double>> distancesBetweenBreweries = new HashMap<>();
    private HashMap<Integer, BreweryPath> cache = new HashMap<>();

    /**
     * Finds the optimal path to visit as many breweries as possible from a given starting point, where the
     * maximum traverseble distance is a configurable application parameter.
     * @param startingPoint - starting point of the path.
     * @return list of {@link BreweryLocationNode} which represents the path from starting location to starting location
     * by visiting as many breweries as possible.
     *
     * The path is calculated roughly as follows:
     * To find the best path from A to B, we need to find the best path to B from the immediate neighbours of A, choose it,
     * and add path A to B to it. It's a recursive problem. so:
     *
     * BestPath(A, B, DISTANCE_LEFT) = BestPath(A, X, D1) appened to BestPath(X, B, DISTANCE_LEFT - D1)
     *
     * where BestPath(X, B, DISTANCE_LEFT) is such X that Max(1 + Steps(X1, B), 1 + Steps(X2, B, 1 + Steps(X2, B)...) where X1, X2.. c X
     * are A neighbours, considering distance left.
     * and BestPath(A, X) is simply A to the optimal X (one step).
     *
     * For slight speed improvement cache is used.
     */
    public List<BreweryLocationNode> findBestPathFromStartingPoint(BreweryLocationNode startingPoint) {
        initialize(startingPoint);
        BreweryPath path = getPath(startingPoint, startingPoint, pathFinderConfig.getMaxDistanceKm());
        return unwrapPath(path);
    }

    private BreweryPath getPath(BreweryLocationNode from, BreweryLocationNode finalDestination, Double distanceLeft
    ) {

        BreweryPath cachedPath = getFromCache(from, finalDestination, distanceLeft);
        if (cachedPath != null) {
            unmarkNodeAsVisited(from);
            return cachedPath;
        }

        HashMap<BreweryLocationNode, Double> neighbouringBreweries = distancesBetweenBreweries.get(from);
        Integer maxBreweriesVisited = -1;
        BreweryPath longestBreweryPath = null;
        markNodeAsVisited(from);

        if (distanceLeft <= 0) {
            unmarkNodeAsVisited(from);
            return null;
        }

        if (nodeIsTheStartingNode(from, finalDestination) && !Objects.equals(distanceLeft,
                pathFinderConfig.getMaxDistanceKm())) {
            return new BreweryPath(finalDestination, null, 0);
        }

        for (Map.Entry<BreweryLocationNode, Double> neighbourBreweryAndDistance : neighbouringBreweries.entrySet()) {
            if (neighbourBreweryAndDistance.getValue() > 0) {
                Double distanceToNextBrewery = neighbourBreweryAndDistance.getValue();
                BreweryLocationNode nextBrewery = neighbourBreweryAndDistance.getKey();
                if (!nextBrewery.isTraversed() || nodeIsTheStartingNode(nextBrewery, finalDestination)) {
                    Double newDistance = distanceLeft - distanceToNextBrewery;

                    BreweryPath breweryPath = getPath(nextBrewery, finalDestination, newDistance);

                    if (breweryPath != null && breweryPath.getSteps() > maxBreweriesVisited) {
                        breweryPath.setDistanceFromPrevious(distanceToNextBrewery);
                        maxBreweriesVisited = breweryPath.getSteps();
                        System.out.println("Longest path yet: " + maxBreweriesVisited);
                        longestBreweryPath = breweryPath;
                    }
                }
            }

        }
        unmarkNodeAsVisited(from);

        if (longestBreweryPath == null) {
            return null;
        }

        putToCache(from, finalDestination, distanceLeft, longestBreweryPath);
        return new BreweryPath(from, longestBreweryPath,longestBreweryPath.getSteps() + 1);
    }

    private boolean nodeIsTheStartingNode(BreweryLocationNode from, BreweryLocationNode finalDestination) {
        return Objects.equals(from.getBreweryId(), finalDestination.getBreweryId());
    }

    private void initialize(BreweryLocationNode startingPoint) {
        log.debug("Initialization started");
        if (this.distancesBetweenBreweries.size() < 1) {
            List<Brewery> breweryList = breweryDao.list();
            List<Geocode> geocodeList = geocodeDao.list();
            List<BreweryLocationNode> breweryLocationNodeList = new ArrayList<>();

            for (Brewery brewery : breweryList) {
                Optional<Geocode> geocodeOfBrewery = findGeocodeOfBrewery(brewery, geocodeList);
                if (geocodeOfBrewery.isPresent()) {
                    BreweryLocationNode breweryLocationNode = new BreweryLocationNode(brewery.getId(),
                            geocodeOfBrewery.get().getLatitude(),
                            geocodeOfBrewery.get().getLongitude(), brewery.getName());

                    breweryLocationNodeList.add(breweryLocationNode);
                }
            }

            breweryLocationNodeList.add(startingPoint);

            this.distancesBetweenBreweries = breweryDistanceMatrixBuilder
                    .buildDistancesBetweenBreweries(breweryLocationNodeList, pathFinderConfig.getMaxDistanceKm());
        }
        log.debug("Initialization finished");
    }

    private void markNodeAsVisited(BreweryLocationNode node) {
        node.setTraversed(true);
    }

    private void unmarkNodeAsVisited(BreweryLocationNode node) {
        node.setTraversed(false);
    }

    private Optional<Geocode> findGeocodeOfBrewery(Brewery brewery, List<Geocode> geocodeList) {
        return geocodeList.stream().filter(geocode -> Objects.equals(geocode.getBreweryId(), brewery.getId())).findFirst();
    }

    private List<BreweryLocationNode> unwrapPath(BreweryPath path) {
        if (path == null) {
            return new ArrayList<>();
        }
        List<BreweryLocationNode> breweryLocationNodeList = new ArrayList<>();

        double distanceFromPrevious = path.getDistanceFromPrevious() == null ? 0 : path.getDistanceFromPrevious();
        BreweryLocationNode brewery = new BreweryLocationNode(path.getBrewery().getBreweryId(), path.getBrewery().getLatitude(),
                path.getBrewery().getLongitude(), path.getBrewery().getName());
        brewery.setDistanceFromOptimalBrewery(distanceFromPrevious);
        breweryLocationNodeList.add(brewery);
        breweryLocationNodeList.addAll(unwrapPath(path.getNextNode()));
        return breweryLocationNodeList;
    }

    private BreweryPath getFromCache(BreweryLocationNode from, BreweryLocationNode finalDestination, Double distanceLeft) {
        Integer hash = MathUtils.hash(from, finalDestination, distanceLeft);
        return cache.get(hash);
    }

    private void putToCache(BreweryLocationNode from, BreweryLocationNode finalDestination, Double distanceLeft,
                            BreweryPath longestBreweryPath) {
        Integer hash = MathUtils.hash(from, finalDestination, distanceLeft);
        cache.put(hash, longestBreweryPath);
    }

}
