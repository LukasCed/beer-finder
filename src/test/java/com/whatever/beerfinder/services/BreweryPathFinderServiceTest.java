package com.whatever.beerfinder.services;

import com.whatever.beerfinder.dao.BeerDataDao;
import com.whatever.beerfinder.models.BreweryLocation;
import com.whatever.beerfinder.utils.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Stack;

import static org.mockito.Mockito.when;

public class BreweryPathFinderServiceTest {

    private AutoCloseable mocksClosable;
    private BreweryPathFinderService breweryPathFinderService;

    @Mock
    private BeerDataDao beerDataDao;

    @BeforeEach
    public void setUp() {
        mocksClosable = MockitoAnnotations.openMocks(this);
        breweryPathFinderService = new BreweryPathFinderService(beerDataDao);

        when(beerDataDao.listBreweries()).thenReturn(TestData.BREWERIES);
        when(beerDataDao.listGeocodes()).thenReturn(TestData.GEOCODES);
    }

    @AfterEach
    public void cleanUp() throws Exception {
        mocksClosable.close();
    }

    @Test
    public void findBestPathFromStartingPoint_success() {
        BreweryLocation startingPoint = new BreweryLocation(-1, 35.01, -97.9, "HOME");
        Stack<BreweryLocation> bestPathFromStartingPoint = breweryPathFinderService.findBestPathFromStartingPoint(startingPoint);
        int k = 7;
    }

}
