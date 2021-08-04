package com.whatever.beerfinder.utils;

import com.whatever.beerfinder.entities.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.whatever.beerfinder.entities.AccuracyEnum.*;
import static com.whatever.beerfinder.entities.AccuracyEnum.APPROXIMATE;

public class TestData {

    public static final List<Geocode> GEOCODES = new ArrayList<>(Arrays.asList(
            new Geocode(1, 1, 30.223400115966797, -97.76969909667969, ROOFTOP),
            new Geocode(2, 2, 32.782501220703125, -100.39299774169922, ROOFTOP),
            new Geocode(3, 3, 31.76679992675781, -98.30810022354126, RANGE_INTERPOLATED),
            new Geocode(4, 4, 45.74509811401367, -102.213500022888184, APPROXIMATE),
            new Geocode(5, 5, 39.55547121891566, -97.93270501363254, APPROXIMATE)
    ));
    public static final List<Beer> BEERS = new ArrayList<>(Arrays.asList(
            new Beer(1, 1, "Wolfiukas", 1, 1, 4.5, 0.0, 0.0, 0, "", "Skaniausias", 0, new Timestamp(System.currentTimeMillis())),
            new Beer(2, 2, "Utenos", 1, 1, 4.5, 0.0, 0.0, 0, "", "Neblogas", 0, new Timestamp(System.currentTimeMillis())),
            new Beer(3, 3, "Kalnapilis", 2, 1, 4.5, 0.0, 0.0, 0, "", "Skanus", 0, new Timestamp(System.currentTimeMillis())),
            new Beer(5, 5, "Rinktinis", 2, 1, 4.5, 0.0, 0.0, 0, "", "Hipsteriskas", 0, new Timestamp(System.currentTimeMillis())),
            new Beer(4, 4, "Optima linija", 2, 2, 4.5, 0.0, 0.0, 0, "", "Geras", 0, new Timestamp(System.currentTimeMillis()))
    ));
    public static final List<Brewery> BREWERIES = new ArrayList<>(Arrays.asList(
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
                    "www.kazkas4.lt", "", "Nieko sau", 0, new Timestamp(System.currentTimeMillis())),
            new Brewery(5, "Brewery 5", "Kalvariju 948", "", "Vilnius", "Vilnius",
                    "54885", "Lithuania", "5857465",
                    "www.kazkas5.lt", "", "Very gut", 0, new Timestamp(System.currentTimeMillis()))
    ));

}
