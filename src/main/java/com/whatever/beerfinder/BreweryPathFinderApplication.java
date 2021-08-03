package com.whatever.beerfinder;

import com.whatever.beerfinder.controllers.BreweryPathFinderController;
import com.whatever.beerfinder.models.BreweryLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Stack;

@SpringBootApplication
public class BreweryPathFinderApplication implements CommandLineRunner {

    @Autowired
    private BreweryPathFinderController breweryPathFinderController;

    public static void main(String[] args) {
        SpringApplication.run(BreweryPathFinderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            double lat = Double.parseDouble(args[0]);
            double longt = Double.parseDouble(args[1]);
            Stack<BreweryLocation> bestPathFromStartingPoint =
                    breweryPathFinderController.findBestPathFromStartingPoint(lat, longt);
            bestPathFromStartingPoint.forEach(System.out::println);
        } catch (NumberFormatException e) {
            System.out.println("Bad arguments! Use this format: x y where x is a floating point number for latitude and" +
                    " y is floating point number for longitude");
        }
    }

}
