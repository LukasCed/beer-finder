package com.whatever.beerfinder.controllers;

import com.whatever.beerfinder.models.BeerType;
import com.whatever.beerfinder.models.BreweryLocation;
import com.whatever.beerfinder.services.BeerCollectorService;
import com.whatever.beerfinder.services.BreweryPathFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BreweryAndBeerFinderController {

    private final BreweryPathFinderService breweryPathFinderService;
    private final BeerCollectorService beerCollectorService;

    public List<BreweryLocation> findBestPathFromStartingPoint(double lat, double longt) {
        BreweryLocation home = new BreweryLocation(-1, lat, longt, "HOME");
        return breweryPathFinderService.findBestPathFromStartingPoint(home);
    }

    public List<BeerType> findRelatedBeers(List<Integer> breweryIdList) {
        return beerCollectorService.listBeerTypesFromBreweries(breweryIdList);
    }
}
