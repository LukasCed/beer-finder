package com.whatever.beerfinder.services;

import com.whatever.beerfinder.builders.BreweryDistanceMatrixBuilder;
import com.whatever.beerfinder.config.PathFinderConfig;
import com.whatever.beerfinder.dao.interfaces.BreweryDao;
import com.whatever.beerfinder.dao.interfaces.GeocodeDao;
import com.whatever.beerfinder.entities.Brewery;
import com.whatever.beerfinder.entities.Geocode;
import com.whatever.beerfinder.models.BreweryNode;
import com.whatever.beerfinder.models.BreweryPath;
import com.whatever.beerfinder.models.BreweryTreeMap;
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
    private HashMap<BreweryNode, BreweryTreeMap> distancesBetweenBreweries = new HashMap<>();

    /**
     * Finds the optimal path to visit as many breweries as possible from a given starting point, where the
     * maximum traverseble distance is a configurable application parameter.
     *
     * @param startingPoint - starting point of the path.
     * @return list of {@link BreweryNode} which represents the path from starting location to starting location
     * by visiting as many breweries as possible.
     * <p>
     * The path is calculated roughly as follows:
     * To find the best path from A to B, we need to find the best path to B from the immediate neighbours of A, choose it,
     * and add path A to B to it. It's a recursive problem. so:
     * <p>
     * BestPath(A, B, DISTANCE_LEFT) = BestPath(A, X, D1) appened to BestPath(X, B, DISTANCE_LEFT - D1)
     * <p>
     * where BestPath(X, B, DISTANCE_LEFT) is such X that Max(1 + Steps(X1, B), 1 + Steps(X2, B, 1 + Steps(X2, B)...) where X1, X2.. c X
     * are A neighbours, considering distance left.
     * and BestPath(A, X) is simply A to the optimal X (one step).
     */
    public List<BreweryNode> findBestPathFromStartingPoint(BreweryNode startingPoint) {
        log.info("Starting the best find calculating function - please stand by..");
        initialize(startingPoint);
        BreweryPath path = getPath(startingPoint, startingPoint, pathFinderConfig.getMaxDistanceKm(), 0);
        return unwrapPath(path);
    }

    private BreweryPath getPath(BreweryNode from, BreweryNode finalDestination, Double distanceLeft, int depth
    ) {
        if (depth > pathFinderConfig.getMaxDepth()) {
            return null;
        }
        BreweryTreeMap neighbouringBreweries = distancesBetweenBreweries.get(from);
        Integer maxBreweriesVisited = -1;
        BreweryPath longestBreweryPath = null;

        if (nodeIsTheStartingNode(from, finalDestination) && !Objects.equals(distanceLeft,
                pathFinderConfig.getMaxDistanceKm())) {
            return new BreweryPath(finalDestination, null, 0);
        }
        markNodeAsVisited(from);

        for (Map.Entry<Double, BreweryNode> neighbourBreweryAndDistance : neighbouringBreweries.entrySet()) {
            Double distanceToNextBrewery = neighbourBreweryAndDistance.getKey();
            BreweryNode nextBrewery = neighbourBreweryAndDistance.getValue();

            BreweryPath pathFromNextNode = getPathFromNextNode(distanceToNextBrewery, nextBrewery, finalDestination,
                    distanceLeft, depth);

            if (pathFromNextNode != null && pathFromNextNode.getSteps() > maxBreweriesVisited) {
                pathFromNextNode.setDistanceFromPrevious(distanceToNextBrewery);
                maxBreweriesVisited = pathFromNextNode.getSteps();
                longestBreweryPath = pathFromNextNode;
                if (maxBreweriesVisited >= (pathFinderConfig.getMaxDepth() - 1))
                    return new BreweryPath(from, longestBreweryPath, longestBreweryPath.getSteps() + 1);
            }
        }
        unmarkNodeAsVisited(from);

        if (longestBreweryPath == null) {
            return null;
        }

        return new BreweryPath(from, longestBreweryPath, longestBreweryPath.getSteps() + 1);
    }

    private BreweryPath getPathFromNextNode(Double distanceToNextBrewery, BreweryNode nextBrewery,
                                            BreweryNode finalDestination, double distanceLeft, int depth) {
        if (distanceToNextBrewery > 0 && distanceLeft - distanceToNextBrewery > 0) {
            if (!nextBrewery.isTraversed() || nodeIsTheStartingNode(nextBrewery, finalDestination)) {
                return getPath(nextBrewery, finalDestination, distanceLeft - distanceToNextBrewery,
                        depth + 1);
            }
        }
        return null;
    }

    private boolean nodeIsTheStartingNode(BreweryNode from, BreweryNode finalDestination) {
        return Objects.equals(from.getBreweryId(), finalDestination.getBreweryId());
    }

    private void initialize(BreweryNode startingPoint) {
        log.debug("Initialization of neighbours and distances into matrix started");
        if (this.distancesBetweenBreweries.size() < 1) {
            List<Brewery> breweryList = breweryDao.list();
            List<Geocode> geocodeList = geocodeDao.list();
            List<BreweryNode> breweryNodeList = new ArrayList<>();

            for (Brewery brewery : breweryList) {
                Optional<Geocode> geocodeOfBrewery = findGeocodeOfBrewery(brewery, geocodeList);
                if (geocodeOfBrewery.isPresent()) {
                    BreweryNode breweryNode = new BreweryNode(brewery.getId(),
                            geocodeOfBrewery.get().getLatitude(),
                            geocodeOfBrewery.get().getLongitude(), brewery.getName());

                    breweryNodeList.add(breweryNode);
                }
            }

            breweryNodeList.add(startingPoint);

            this.distancesBetweenBreweries = breweryDistanceMatrixBuilder
                    .buildDistancesBetweenBreweries(breweryNodeList, pathFinderConfig.getMaxDistanceKm(),
                            startingPoint);
        }
        log.debug("Initialization finished");
    }

    private void markNodeAsVisited(BreweryNode node) {
        node.setTraversed(true);
    }

    private void unmarkNodeAsVisited(BreweryNode node) {
        node.setTraversed(false);
    }

    private Optional<Geocode> findGeocodeOfBrewery(Brewery brewery, List<Geocode> geocodeList) {
        return geocodeList.stream().filter(geocode -> Objects.equals(geocode.getBreweryId(), brewery.getId())).findFirst();
    }

    private List<BreweryNode> unwrapPath(BreweryPath path) {
        if (path == null) {
            return new ArrayList<>();
        }
        List<BreweryNode> breweryNodeList = new ArrayList<>();

        double distanceFromPrevious = path.getDistanceFromPrevious() == null ? 0 : path.getDistanceFromPrevious();
        BreweryNode brewery = new BreweryNode(path.getBrewery().getBreweryId(), path.getBrewery().getLatitude(),
                path.getBrewery().getLongitude(), path.getBrewery().getName());
        brewery.setDistanceFromPreviousOptimalBrewery(distanceFromPrevious);
        breweryNodeList.add(brewery);
        breweryNodeList.addAll(unwrapPath(path.getNextNode()));
        return breweryNodeList;
    }

}
