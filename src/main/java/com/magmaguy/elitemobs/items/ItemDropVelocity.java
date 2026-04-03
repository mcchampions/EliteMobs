package com.magmaguy.elitemobs.items;

import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Created by MagmaGuy on 07/10/2016.
 */
public class ItemDropVelocity {

    public static Vector ItemDropVelocity() {

        Random random = new Random();

        double x = (random.nextDouble() - 0.5) / 3;
        double y = 0.5;
        double z = (random.nextDouble() - 0.5) / 3;

        return new Vector(x, y, z);

    }

}
