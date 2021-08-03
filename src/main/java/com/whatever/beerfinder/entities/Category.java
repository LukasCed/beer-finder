package com.whatever.beerfinder.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Category {
    private final int id;
    private final String catName;
    private final Timestamp lastMod;
}
