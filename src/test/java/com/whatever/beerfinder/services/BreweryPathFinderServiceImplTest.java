package com.whatever.beerfinder.services;

import com.whatever.beerfinder.config.PathFinderConfig;
import com.whatever.beerfinder.dao.BeerDaoImpl;
import com.whatever.beerfinder.dao.interfaces.BeerDao;
import com.whatever.beerfinder.dao.interfaces.BreweryDao;
import com.whatever.beerfinder.dao.interfaces.GeocodeDao;
import com.whatever.beerfinder.models.BreweryLocationNode;
import com.whatever.beerfinder.utils.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class BreweryPathFinderServiceImplTest {

    private AutoCloseable mocksClosable;
    private BreweryPathFinderServiceImpl breweryPathFinderServiceImpl;

    @Mock
    private GeocodeDao geocodeDao;
    @Mock
    private BreweryDao breweryDao;
    @Mock
    private PathFinderConfig pathFinderConfig;

    @BeforeEach
    public void setUp() {
        mocksClosable = MockitoAnnotations.openMocks(this);
        breweryPathFinderServiceImpl = new BreweryPathFinderServiceImpl(pathFinderConfig, breweryDao, geocodeDao);

        when(breweryDao.list()).thenReturn(TestData.BREWERIES);
        when(geocodeDao.list()).thenReturn(TestData.GEOCODES);
        when(pathFinderConfig.getMaxDistanceKm()).thenReturn(20000.0);
    }

    @AfterEach
    public void cleanUp() throws Exception {
        mocksClosable.close();
    }

    @Test
    public void findBestPathFromStartingPoint_success() {
        BreweryLocationNode startingPoint = new BreweryLocationNode(-1, 35.01, -97.9, "HOME");
        List<BreweryLocationNode> bestPathFromStartingPoint = breweryPathFinderServiceImpl.findBestPathFromStartingPoint(startingPoint);
        assertNotNull(bestPathFromStartingPoint);

        Assertions.assertThat(bestPathFromStartingPoint)
                .hasSize(7)
                .extracting(BreweryLocationNode::getBreweryId)
                .containsExactlyInAnyOrder(-1, 5, 1, 2, 4, 3, -1);
    }

    @Test
    public void findBestPathFromStartingPoint_pathIsEmpty_ifStartingPointTooFarAway() {
        BreweryLocationNode startingPoint = new BreweryLocationNode(-1, -90.00, -90.0, "HOME");
        List<BreweryLocationNode> bestPathFromStartingPoint = breweryPathFinderServiceImpl.findBestPathFromStartingPoint(startingPoint);
        assertNotNull(bestPathFromStartingPoint);

        Assertions.assertThat(bestPathFromStartingPoint)
                .hasSize(0);
    }

}
