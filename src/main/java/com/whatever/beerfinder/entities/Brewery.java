package com.whatever.beerfinder.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Brewery {
    @Id
    private int id;
    @Column
    private String name;

    private Brewery() {

    }
}
