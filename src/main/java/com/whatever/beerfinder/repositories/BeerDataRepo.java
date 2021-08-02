package com.whatever.beerfinder.repositories;

import com.whatever.beerfinder.models.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.whatever.beerfinder.models.AccuracyEnum.*;

@Repository
public class BeerDataRepo {

    private List<Geocode> geocodes = new ArrayList<>(Arrays.asList(
            new Geocode(1, 1, 30.223400115966797, -97.76969909667969, ROOFTOP),
            new Geocode(2, 2, 32.782501220703125, -100.39299774169922, ROOFTOP),
            new Geocode(3, 3, 31.76679992675781, -98.30810022354126, RANGE_INTERPOLATED),
            new Geocode(4, 4, 45.74509811401367, -102.213500022888184, APPROXIMATE)
    ));
    private List<Beer> beers = new ArrayList<>(Arrays.asList(
            new Beer(1, 1, "Wolfiukas", 1, 1, 4.5, 0.0, 0.0, 0, "", "Skaniausias", 0, new Timestamp(System.currentTimeMillis())),
            new Beer(2, 2, "Utenos", 1, 1, 4.5, 0.0, 0.0, 0, "", "Neblogas", 0, new Timestamp(System.currentTimeMillis())),
            new Beer(3, 3, "Kalnapilis", 2, 1, 4.5, 0.0, 0.0, 0, "", "Skanus", 0, new Timestamp(System.currentTimeMillis())),
            new Beer(4, 4, "Optima linija", 2, 2, 4.5, 0.0, 0.0, 0, "", "Geras", 0, new Timestamp(System.currentTimeMillis()))
            ));
    private List<Brewery> breweries = new ArrayList<>(Arrays.asList(
            new Brewery(1, "Brewery 1", "Kalvariju 4", "", "Vilnius", "Vilnius",
                    "1244", "Lithuania", "86885566",
                    "www.kazkas1.lt", "", "Labai geras brewery", 0, new Timestamp(System.currentTimeMillis())),
            new Brewery(2, "Brewery 2", "Kalvariju 14", "", "Vilnius", "Vilnius",
                    "1254", "Lithuania", "86155566",
                    "www.kazkas2.lt", "", "Dar geresnis", 0, new Timestamp(System.currentTimeMillis())),
            new Brewery(3, "Brewery 3", "Kalvariju 27", "", "Vilnius", "Vilnius",
                    "1274", "Lithuania", "86852566",
                    "www.kazkas3.lt", "", "Pats geriausias", 0, new Timestamp(System.currentTimeMillis())),
            new Brewery(4, "Brewery 4", "Kalvariju 58", "", "Vilnius", "Vilnius",
                    "1234", "Lithuania", "86855546",
                    "www.kazkas4.lt", "", "Nieko sau", 0, new Timestamp(System.currentTimeMillis()))
    ));
    private List<Category> categories = new ArrayList<>(
            Arrays.asList(
                    new Category(1, "Skanus", new Timestamp(System.currentTimeMillis())),
                    new Category(2, "Neskanus", new Timestamp(System.currentTimeMillis()))
            )
    );
    private List<Style> styles = new ArrayList<>(Arrays.asList(
            new Style(1, 1, "Saldus", new Timestamp(System.currentTimeMillis())),
            new Style(2, 2, "Porugstis", new Timestamp(System.currentTimeMillis()))
    )
    );

    private List<Geocode> listGeocodes() {
        return geocodes;
    }

    private List<Beer> listBeers() {
        return beers;
    }

    private List<Brewery> listBreweries() {
        return breweries;
    }

    private List<Category> listCategories() {
        return categories;
    }

    private List<Style> listStyles() {
        return styles;
    }
}
