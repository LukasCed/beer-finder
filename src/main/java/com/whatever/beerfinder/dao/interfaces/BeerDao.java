package com.whatever.beerfinder.dao.interfaces;

import com.whatever.beerfinder.entities.Beer;

import java.util.List;

public interface BeerDao extends DataDao<Beer> {

    List<Beer> listBeers(List<Integer> breweryIds);

}
