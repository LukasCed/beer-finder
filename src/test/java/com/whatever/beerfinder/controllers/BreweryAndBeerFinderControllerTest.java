package com.whatever.beerfinder.controllers;

import com.whatever.beerfinder.models.BreweryNode;
import com.whatever.beerfinder.services.BeerCollectorServiceImpl;
import com.whatever.beerfinder.services.BeerCollectorServiceImplTest;
import com.whatever.beerfinder.services.BreweryPathFinderServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
        List<BreweryNode> breweryNodes = new ArrayList<>();
        breweryNodes.add(new BreweryNode(14, 1.0, 1.4, "test"));

        ArgumentCaptor<BreweryNode> breweryLocationArgumentCaptor = ArgumentCaptor.forClass(BreweryNode.class);
        when(breweryPathFinderServiceImpl.findBestPathFromStartingPoint(any()))
                .thenReturn(breweryNodes);

        List<BreweryNode> bestPathFromStartingPoint = breweryAndBeerFinderController.findBestPathFromStartingPoint(1.0, 1.4);

        verify(breweryPathFinderServiceImpl, Mockito.times(1))
                .findBestPathFromStartingPoint(breweryLocationArgumentCaptor.capture());

        BreweryNode passedBreweryNode = breweryLocationArgumentCaptor.getValue();
        assertEquals(-1, passedBreweryNode.getBreweryId());
        assertEquals(1.0, passedBreweryNode.getLatitude());
        assertEquals(1.4, passedBreweryNode.getLongitude());
        assertEquals("HOME", passedBreweryNode.getName());
        assertNotNull(bestPathFromStartingPoint);
        assertEquals(1, bestPathFromStartingPoint.size());
        assertEquals(14, bestPathFromStartingPoint.get(0).getBreweryId());
    }
}
