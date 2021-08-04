package com.whatever.beerfinder;

import com.whatever.beerfinder.controllers.BreweryAndBeerFinderController;
import com.whatever.beerfinder.models.BeerType;
import com.whatever.beerfinder.models.BreweryLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class BreweryAndBeerFinderApplication implements CommandLineRunner {

    @Autowired
    private BreweryAndBeerFinderController breweryAndBeerFinderController;

    public static void main(String[] args) {
        SpringApplication.run(BreweryAndBeerFinderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            double lat = Double.parseDouble(args[0]);
            double longt = Double.parseDouble(args[1]);

            List<BreweryLocation> bestPathFromStartingPoint = breweryAndBeerFinderController
                    .findBestPathFromStartingPoint(lat, longt);
            List<Integer> ids = bestPathFromStartingPoint.stream()
                    .map(BreweryLocation::getBreweryId)
                    .collect(Collectors.toList());
            List<BeerType> relatedBeers = breweryAndBeerFinderController.findRelatedBeers(ids);

            bestPathFromStartingPoint.forEach(System.out::println);
            relatedBeers.forEach(System.out::println);
        } catch (NumberFormatException e) {
            System.out.println("Bad arguments! Use this format: x y where x is a floating point number for latitude and" +
                    " y is floating point number for longitude");
        }
    }

}
