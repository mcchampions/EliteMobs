package com.magmaguy.elitemobs.utils;

import com.magmaguy.elitemobs.items.customitems.CustomItem;

import java.util.HashMap;
import java.util.Map;

public class WeightedProbability {

    public static String pickWeighedProbability(HashMap<String, Double> weighedValues) {
        double totalWeight = 0;
        for (Double v : weighedValues.values())
            totalWeight += v;
        String selectedString = null;
        double random = Math.random() * totalWeight;
        for (Map.Entry<String, Double> entry : weighedValues.entrySet()) {
            random -= entry.getValue();
            if (random <= 0) {
                selectedString = entry.getKey();
                break;
            }
        }
        return selectedString;
    }

    public static CustomItem pickWeighedProbabilityFromCustomItems(HashMap<CustomItem, Double> weighedValues) {
        double totalWeight = 0;
        for (Double v : weighedValues.values())
            totalWeight += v;
        CustomItem selectedCustomItem = null;
        double random = Math.random() * totalWeight;
        for (Map.Entry<CustomItem, Double> entry : weighedValues.entrySet()) {
            random -= entry.getValue();
            if (random <= 0) {
                selectedCustomItem = entry.getKey();
                break;
            }
        }
        return selectedCustomItem;
    }

}
