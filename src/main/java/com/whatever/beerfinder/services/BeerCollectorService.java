package com.whatever.beerfinder.services;

import com.whatever.beerfinder.dao.BeerDataDao;
import com.whatever.beerfinder.models.BeerType;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BeerCollectorService {

    private final BeerDataDao beerDataDao;

    public List<BeerType> listBeerTypesFromBreweries(List<Integer> breweryIdList) {
        return beerDataDao.listBeers(breweryIdList).stream()
                .map(x -> new BeerType(x.getName()))
                .collect(Collectors.toList());
    }
}
