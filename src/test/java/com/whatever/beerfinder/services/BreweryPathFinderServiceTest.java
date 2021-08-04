package com.whatever.beerfinder.services;

import com.whatever.beerfinder.dao.BeerDataDao;
import com.whatever.beerfinder.models.BreweryLocation;
import com.whatever.beerfinder.utils.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        List<BreweryLocation> bestPathFromStartingPoint = breweryPathFinderService.findBestPathFromStartingPoint(startingPoint);
        assertNotNull(bestPathFromStartingPoint);
        assertEquals(7, bestPathFromStartingPoint.size());
        assertEquals(-1, bestPathFromStartingPoint.get(0).getBreweryId());
        assertEquals(0, bestPathFromStartingPoint.get(0).getDistanceFromOptimalBrewery());
        assertEquals(5, bestPathFromStartingPoint.get(1).getBreweryId());
        assertEquals(505.441, bestPathFromStartingPoint.get(1).getDistanceFromOptimalBrewery(), 0.1);
        assertEquals(1, bestPathFromStartingPoint.get(2).getBreweryId());
        assertEquals(1037.78, bestPathFromStartingPoint.get(2).getDistanceFromOptimalBrewery(), 0.1);
        assertEquals(2, bestPathFromStartingPoint.get(3).getBreweryId());
        assertEquals(377.893, bestPathFromStartingPoint.get(3).getDistanceFromOptimalBrewery(), 0.1);
        assertEquals(4, bestPathFromStartingPoint.get(4).getBreweryId());
        assertEquals(1449.761, bestPathFromStartingPoint.get(4).getDistanceFromOptimalBrewery(), 0.1);
        assertEquals(3, bestPathFromStartingPoint.get(5).getBreweryId());
        assertEquals(1590.2471, bestPathFromStartingPoint.get(5).getDistanceFromOptimalBrewery(), 0.1);
        assertEquals(-1, bestPathFromStartingPoint.get(6).getBreweryId());
        assertEquals(362.611, bestPathFromStartingPoint.get(6).getDistanceFromOptimalBrewery(), 0.1);
    }

}
