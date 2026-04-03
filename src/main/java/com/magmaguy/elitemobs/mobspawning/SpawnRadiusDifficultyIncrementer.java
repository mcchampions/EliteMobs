package com.magmaguy.elitemobs.mobspawning;

import com.magmaguy.elitemobs.config.MobCombatSettingsConfig;
import org.bukkit.Location;

public class SpawnRadiusDifficultyIncrementer {

    public static int distanceFromSpawnLevelIncrease(Location location) {

        double distanceUnit = MobCombatSettingsConfig.getDistanceToIncrement();
        double levelToIncrement = MobCombatSettingsConfig.getLevelToIncrement();

        Location spawnLocation = location.getWorld().getSpawnLocation();

        double distanceFromSpawn = spawnLocation.distance(location);

        return (int) (distanceFromSpawn / distanceUnit * levelToIncrement);

    }

}
