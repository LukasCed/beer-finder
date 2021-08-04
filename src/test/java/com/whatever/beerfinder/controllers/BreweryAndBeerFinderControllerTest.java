package com.whatever.beerfinder.controllers;

import com.whatever.beerfinder.models.BreweryLocation;
import com.whatever.beerfinder.services.BeerCollectorService;
import com.whatever.beerfinder.services.BreweryPathFinderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class BreweryAndBeerFinderControllerTest {

    private AutoCloseable mocksClosable;
    private BreweryAndBeerFinderController breweryAndBeerFinderController;
    @Mock
    private BreweryPathFinderService breweryPathFinderService;
    @Mock
    private BeerCollectorService beerCollectorService;

    @BeforeEach
    public void setUp() {
        mocksClosable = MockitoAnnotations.openMocks(this);
        breweryAndBeerFinderController = new BreweryAndBeerFinderController(breweryPathFinderService,
                beerCollectorService);
    }

    @AfterEach
    public void cleanUp() throws Exception {
        mocksClosable.close();
    }

    @Test
    public void findBestPathFromStartingPoint_success() {
        Stack<BreweryLocation> breweryLocations = new Stack<>();
        breweryLocations.push(new BreweryLocation(14, 1.0, 1.4, "test"));

        ArgumentCaptor<BreweryLocation> breweryLocationArgumentCaptor = ArgumentCaptor.forClass(BreweryLocation.class);
        when(breweryPathFinderService.findBestPathFromStartingPoint(breweryLocationArgumentCaptor.capture()))
                .thenReturn(breweryLocations);

        List<BreweryLocation> bestPathFromStartingPoint = breweryAndBeerFinderController.findBestPathFromStartingPoint(1.0, 1.4);

        BreweryLocation passedBreweryLocation = breweryLocationArgumentCaptor.getValue();
        assertEquals(-1, passedBreweryLocation.getBreweryId());
        assertEquals(1.0, passedBreweryLocation.getLatitude());
        assertEquals(1.4, passedBreweryLocation.getLongitude());
        assertEquals("HOME", passedBreweryLocation.getName());
        assertNotNull(bestPathFromStartingPoint);
        assertEquals(1, bestPathFromStartingPoint.size());
        assertEquals(14, bestPathFromStartingPoint.get(0).getBreweryId());
    }
}
