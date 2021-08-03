package com.whatever.beerfinder.entities;

import javax.persistence.Entity;

@Entity
public enum AccuracyEnum {
    ROOFTOP, RANGE_INTERPOLATED, APPROXIMATE, GEOMETRIC_CENTER
}
