package com.whatever.beerfinder.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@Setter
@Getter
public class BreweryTreeMap {
    private TreeMap<Double, BreweryNode> treeMap = new TreeMap<>();
    private ArrayList<Map.Entry<Double, BreweryNode>> entries = new ArrayList<>();

    /**
     * This method amongst saving map entry internally very kindly saves the provided {@link BreweryNode} as the first to visit.
     * It's useful for gaining access to home node first, before any other.
     *
     * @param key   distance
     * @param value breweryNode
     * @return {@link BreweryTreeMap}
     */
    public BreweryTreeMap put(Double key, BreweryNode value) {
        if (key != null && value != null && value.getBreweryId() == -1) {
            entries.add(0, Map.entry(key, value));
        } else if (key != null && value != null) {
            treeMap.put(key, value);
        }
        return this;
    }

    /**
     * Internal state modifier. Should be called only when finishing setting up the BreweryTreeMap.
     */
    public void rebalance() {
        entries.addAll(treeMap.entrySet());
    }

    public Iterable<? extends Map.Entry<Double, BreweryNode>> entrySet() {
        return entries;
    }
}
