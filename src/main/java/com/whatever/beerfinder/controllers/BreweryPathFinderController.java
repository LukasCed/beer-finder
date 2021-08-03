package com.whatever.beerfinder.controllers;

import com.whatever.beerfinder.models.BreweryLocation;
import com.whatever.beerfinder.services.BreweryPathFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Stack;

@Controller
@RequiredArgsConstructor
public class BreweryPathFinderController {

    private final BreweryPathFinderService breweryPathFinderService;

    public Stack<BreweryLocation> findBestPathFromStartingPoint(double lat, double longt) {
        BreweryLocation home = new BreweryLocation(-1, lat, longt, "HOME");
        return breweryPathFinderService.findBestPathFromStartingPoint(home);
    }

}
