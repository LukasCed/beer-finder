package com.whatever.beerfinder.utils;

import java.util.Objects;

public class MathUtils {

    private static final Double PI = 3.14159265358979323846;

    public static double calculateHaversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = (lat2 - lat1) * PI / 180.0;
        double dLon = (lon2 - lon1) * PI / 180.0;

        // convert to radians
        lat1 = (lat1) * PI / 180.0;
        lat2 = (lat2) * PI / 180.0;

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        return 6371 * 2 * Math.asin(Math.sqrt(a));
    }

    public static <K, L, M> Integer hash(K first, L second, M third) {
        return Objects.hash(first, second, third);
    }
}
