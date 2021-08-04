package com.whatever.beerfinder.services;

import com.whatever.beerfinder.dao.BeerDaoImpl;
import com.whatever.beerfinder.models.BeerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerCollectorServiceImpl {

    private final BeerDaoImpl beerDataDaoImpl;

    public List<BeerType> listBeerTypesFromBreweries(List<Integer> breweryIdList) {
        return beerDataDaoImpl.listBeers(breweryIdList).stream()
                .map(x -> new BeerType(x.getName()))
                .collect(Collectors.toList());
    }
}
