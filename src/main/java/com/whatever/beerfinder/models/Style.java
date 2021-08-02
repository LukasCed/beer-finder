package com.whatever.beerfinder.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Style {
    private final int id;
    private final int catId;
    private final String styleName;
    private final Timestamp lastMod;
}
