package com.magmaguy.elitemobs.utils;

import org.bukkit.entity.EnderDragon;

public class EnderDragonPhaseSimplifier {
    private EnderDragonPhaseSimplifier() {
    }

    /**
     * Returns whether the dragon's phase has it flying. Strict considers edge behaviors as true (charging, landing and taking off)
     *
     * @param phase  Phase to evaluate
     * @param strict If true, includes all flying phases, including things such as charging players and returning to the portal
     * @return Returns whether the dragon is flying
     */
    public static boolean isFlying(EnderDragon.Phase phase, boolean strict) {
        return switch (phase) {
            case HOVER, CIRCLING, STRAFING -> true;
            default -> {
                if (strict)
                    yield phase.equals(EnderDragon.Phase.LAND_ON_PORTAL) ||
                          phase.equals(EnderDragon.Phase.LEAVE_PORTAL) ||
                          phase.equals(EnderDragon.Phase.CHARGE_PLAYER);
                yield false;
            }
        };
    }

    /**
     * Returns whether the dragon is currently in a landed phase. This means any phase where the dragon is sitting in the middle of the map.
     *
     * @param phase Phase to evaluate
     * @return Whether the dragon sits in the middle of the map during that phase
     */
    public static boolean isLanded(EnderDragon.Phase phase) {
        return switch (phase) {
            case BREATH_ATTACK, SEARCH_FOR_BREATH_ATTACK_TARGET, ROAR_BEFORE_ATTACK -> true;
            default -> false;
        };
    }


}

