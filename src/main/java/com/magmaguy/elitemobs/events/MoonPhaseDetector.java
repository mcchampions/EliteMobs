package com.magmaguy.elitemobs.events;

import com.magmaguy.magmacore.util.Logger;
import org.bukkit.World;

public class MoonPhaseDetector {

    public static MoonPhase detectMoonPhase(World eventWorld) {

        int days = (int) eventWorld.getFullTime() / 24000;
        int phase = Math.abs(days % 8);

        return switch (phase) {
            case 0 -> MoonPhase.FULL_MOON;
            case 1 -> MoonPhase.WANING_GIBBOUS;
            case 2 -> MoonPhase.LAST_QUARTER;
            case 3 -> MoonPhase.WANING_CRESCENT;
            case 4 -> MoonPhase.NEW_MOON;
            case 5 -> MoonPhase.WAXING_CRESCENT;
            case 6 -> MoonPhase.FIRST_QUARTER;
            case 7 -> MoonPhase.WAXING_GIBBOUS;
            default -> {
                Logger.info("Unhandled moon phase. Phase " + phase + " was " + days + ". Defaulting to full moon...");
                yield MoonPhase.FULL_MOON;
            }
        };

    }

    public enum MoonPhase {
        FULL_MOON,
        WANING_GIBBOUS,
        LAST_QUARTER,
        WANING_CRESCENT,
        NEW_MOON,
        WAXING_CRESCENT,
        FIRST_QUARTER,
        WAXING_GIBBOUS
    }

}
