package com.whatever.beerfinder.utils;

import com.whatever.beerfinder.entities.Beer;
import com.whatever.beerfinder.entities.Brewery;
import com.whatever.beerfinder.entities.Geocode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData {

    public static final List<Geocode> GEOCODES = new ArrayList<>(Arrays.asList(
            new Geocode(1, 1, 30.223400115966797, -97.76969909667969),
            new Geocode(2, 2, 32.782501220703125, -100.39299774169922),
            new Geocode(3, 3, 31.76679992675781, -98.30810022354126),
            new Geocode(4, 4, 45.74509811401367, -102.213500022888184),
            new Geocode(5, 5, 39.55547121891566, -97.93270501363254)
    ));
    public static final List<Beer> BEERS = new ArrayList<>(Arrays.asList(
            new Beer(1, 1, "volfo"),
            new Beer(2, 2, "Utenos"),
            new Beer(3, 3, "Kalnapilis"),
            new Beer(5, 5, "Rinktinis"),
            new Beer(4, 4, "Optima linija")
    ));
    public static final List<Brewery> BREWERIES = new ArrayList<>(Arrays.asList(
            new Brewery(1, "Brewery 1"),
            new Brewery(2, "Brewery 2"),
            new Brewery(3, "Brewery 3"),
            new Brewery(4, "Brewery 4"),
            new Brewery(5, "Brewery 5")
    ));

}
