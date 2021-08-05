package com.whatever.beerfinder.builders;

import com.whatever.beerfinder.models.BreweryNode;
import com.whatever.beerfinder.models.BreweryTreeMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BreweryDistanceMatrixBuilderTest {

    private BreweryDistanceMatrixBuilder breweryDistanceMatrixBuilder;

    @BeforeEach
    public void setUp() {
        breweryDistanceMatrixBuilder = new BreweryDistanceMatrixBuilder();
    }

    @Test
    public void buildDistancesBetweenBreweries_success() {
        List<BreweryNode> breweryNodeList = new ArrayList<>(Arrays.asList(
                new BreweryNode(1, 10, 10, "first"),
                new BreweryNode(2, 15, 15, "second"),
                new BreweryNode(3, 18, 18, "third")
                ));

        BreweryNode homeNode = new BreweryNode(-1, 11, 11, "home");
        HashMap<BreweryNode, BreweryTreeMap> breweryNodeBreweryTreeMapHashMap =
                breweryDistanceMatrixBuilder.buildDistancesBetweenBreweries(breweryNodeList, 1000.0, homeNode);

        Set<Map.Entry<BreweryNode, BreweryTreeMap>> entries = breweryNodeBreweryTreeMapHashMap.entrySet();
        assertEquals(3, entries.size());
    }

    @Test
    public void buildDistancesBetweenBreweries_neighboursNotIncluded_ifDistanceIsHigherThanMax() {
        List<BreweryNode> breweryNodeList = new ArrayList<>(Arrays.asList(
                new BreweryNode(1, 10, 10, "first"),
                new BreweryNode(2, 10.1, 10.1, "second"),
                new BreweryNode(3, 50, 50, "third")
        ));

        BreweryNode homeNode = new BreweryNode(-1, 10.2, 10.2, "home");
        HashMap<BreweryNode, BreweryTreeMap> breweryNodeBreweryTreeMapHashMap =
                breweryDistanceMatrixBuilder.buildDistancesBetweenBreweries(breweryNodeList, 100.0, homeNode);

        Set<Map.Entry<BreweryNode, BreweryTreeMap>> entries = breweryNodeBreweryTreeMapHashMap.entrySet();

        Assertions.assertThat(entries)
                .hasSize(3)
                .extracting(x -> x.getValue().getEntries().size())
                .containsExactlyInAnyOrder(0, 2, 2);
    }

}
