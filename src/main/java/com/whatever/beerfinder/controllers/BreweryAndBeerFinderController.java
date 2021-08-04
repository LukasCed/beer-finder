package com.whatever.beerfinder.controllers;

import com.whatever.beerfinder.models.BeerType;
import com.whatever.beerfinder.models.BreweryLocationNode;
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

    public List<BreweryLocationNode> findBestPathFromStartingPoint(double lat, double longt) {
        BreweryLocationNode home = new BreweryLocationNode(-1, lat, longt, "HOME");
        return breweryPathFinderServiceImpl.findBestPathFromStartingPoint(home);
    }

    public List<BeerType> findRelatedBeers(List<Integer> breweryIdList) {
        return beerCollectorServiceImpl.listBeerTypesFromBreweries(breweryIdList);
    }
}
