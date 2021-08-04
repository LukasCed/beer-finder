package com.whatever.beerfinder.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PathFinderConfig {
    @Value("${max-distance-km}")
    private Double maxDistanceKm;
}
