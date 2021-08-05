package com.whatever.beerfinder.services;

import com.whatever.beerfinder.dao.BeerDaoImpl;
import com.whatever.beerfinder.entities.Beer;
import com.whatever.beerfinder.models.BeerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BeerCollectorServiceImplTest {

    private AutoCloseable mocksClosable;
    private BeerCollectorServiceImpl beerCollectorService;

    @Mock
    private BeerDaoImpl beerDao;

    @BeforeEach
    public void setUp() {
        mocksClosable = MockitoAnnotations.openMocks(this);
        beerCollectorService = new BeerCollectorServiceImpl(beerDao);
    }

    @AfterEach
    public void cleanUp() throws Exception {
        mocksClosable.close();
    }

    @Test
    public void listBeerTypesFromBreweries_success() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(17);

        ArgumentCaptor<List<Integer>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        when(beerDao.listBeers(any())).thenReturn(new ArrayList<>(Collections.singletonList(new Beer(100,
                100, "beer"))));

        List<BeerType> beerTypes = beerCollectorService.listBeerTypesFromBreweries(integerList);

        verify(beerDao, times(1)).listBeers(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue().get(0), 1);
        assertEquals(argumentCaptor.getValue().get(1), 17);
        assertNotNull(beerTypes);
        assertEquals(1, beerTypes.size());
        assertEquals("beer", beerTypes.get(0).getTypeName());
    }
}
