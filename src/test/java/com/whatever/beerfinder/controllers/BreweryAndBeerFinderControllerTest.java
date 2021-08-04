package com.whatever.beerfinder.controllers;

import com.whatever.beerfinder.models.BreweryLocationNode;
import com.whatever.beerfinder.services.BeerCollectorServiceImpl;
import com.whatever.beerfinder.services.BreweryPathFinderServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class BreweryAndBeerFinderControllerTest {

    private AutoCloseable mocksClosable;
    private BreweryAndBeerFinderController breweryAndBeerFinderController;
    @Mock
    private BreweryPathFinderServiceImpl breweryPathFinderServiceImpl;
    @Mock
    private BeerCollectorServiceImpl beerCollectorServiceImpl;

    @BeforeEach
    public void setUp() {
        mocksClosable = MockitoAnnotations.openMocks(this);
        breweryAndBeerFinderController = new BreweryAndBeerFinderController(breweryPathFinderServiceImpl,
                beerCollectorServiceImpl);
    }

    @AfterEach
    public void cleanUp() throws Exception {
        mocksClosable.close();
    }

    @Test
    public void findBestPathFromStartingPoint_success() {
        List<BreweryLocationNode> breweryLocationNodes = new ArrayList<>();
        breweryLocationNodes.add(new BreweryLocationNode(14, 1.0, 1.4, "test"));

        ArgumentCaptor<BreweryLocationNode> breweryLocationArgumentCaptor = ArgumentCaptor.forClass(BreweryLocationNode.class);
        when(breweryPathFinderServiceImpl.findBestPathFromStartingPoint(breweryLocationArgumentCaptor.capture()))
                .thenReturn(breweryLocationNodes);

        List<BreweryLocationNode> bestPathFromStartingPoint = breweryAndBeerFinderController.findBestPathFromStartingPoint(1.0, 1.4);

        BreweryLocationNode passedBreweryLocationNode = breweryLocationArgumentCaptor.getValue();
        assertEquals(-1, passedBreweryLocationNode.getBreweryId());
        assertEquals(1.0, passedBreweryLocationNode.getLatitude());
        assertEquals(1.4, passedBreweryLocationNode.getLongitude());
        assertEquals("HOME", passedBreweryLocationNode.getName());
        assertNotNull(bestPathFromStartingPoint);
        assertEquals(1, bestPathFromStartingPoint.size());
        assertEquals(14, bestPathFromStartingPoint.get(0).getBreweryId());
    }
}
