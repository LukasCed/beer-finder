package com.whatever.beerfinder;

import com.whatever.beerfinder.controllers.BreweryAndBeerFinderController;
import com.whatever.beerfinder.models.BeerType;
import com.whatever.beerfinder.models.BreweryNode;
import com.whatever.beerfinder.models.exceptions.ArgumentParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class BreweryAndBeerFinderApplication implements CommandLineRunner {

    @Autowired
    private BreweryAndBeerFinderController breweryAndBeerFinderController;

    public static void main(String[] args) {
        SpringApplication.run(BreweryAndBeerFinderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            double lat = parseArgs(0, args);
            double longt = parseArgs(1, args);

            long startTime = System.currentTimeMillis();
            List<BreweryNode> bestPathFromStartingPoint = breweryAndBeerFinderController
                    .findBestPathFromStartingPoint(lat, longt);
            List<Integer> ids = bestPathFromStartingPoint.stream()
                    .map(BreweryNode::getBreweryId)
                    .collect(Collectors.toList());
            List<BeerType> relatedBeers = breweryAndBeerFinderController.findRelatedBeers(ids);
            long endTime = System.currentTimeMillis();

            bestPathFromStartingPoint.forEach(System.out::println);
            System.out.println("Beer types:");
            relatedBeers.forEach(System.out::println);

            System.out.println("Program took: " + (endTime - startTime) + " ms");
        } catch (ArgumentParseException e) {
            log.error("Bad arguments! Use this format: x.zzz... y.zzz.., where x is any number for latitude, " +
                    "y is any number for longitude, z are decimal digits. For example: 98.4471 85.6432. Provided: {}",
                    (Object) args);
        }
    }

    private double parseArgs(int index, String... args) throws ArgumentParseException {
        try {
            if (args == null || args.length != 2) {
                throw new ArgumentParseException("Invalid number of args provided or no args provided at all! " +
                        "Required: 2", null);
            }
            return Double.parseDouble(args[index]);
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("Invalid argument provided!", e);
        }
    }

}
