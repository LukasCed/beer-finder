package com.whatever.beerfinder.controllers;

import com.whatever.beerfinder.models.BeerType;
import com.whatever.beerfinder.models.BreweryNode;
import com.whatever.beerfinder.services.BeerCollectorServiceImpl;
import com.whatever.beerfinder.services.BreweryPathFinderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BreweryAndBeerFinderController {

    private final BreweryPathFinderServiceImpl breweryPathFinderServiceImpl;
    private final BeerCollectorServiceImpl beerCollectorServiceImpl;

    public List<BreweryNode> findBestPathFromStartingPoint(double lat, double longt) {
        BreweryNode home = new BreweryNode(-1, lat, longt, "HOME");
        return breweryPathFinderServiceImpl.findBestPathFromStartingPoint(home);
    }

    public List<BeerType> findRelatedBeers(List<Integer> breweryIdList) {
        return beerCollectorServiceImpl.listBeerTypesFromBreweries(breweryIdList);
    }
}
