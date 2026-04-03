package com.magmaguy.elitemobs.config;

import com.magmaguy.elitemobs.versionnotifier.VersionChecker;
import org.bukkit.Particle;

import java.util.Locale;

public class LegacyValueConverter {
    private LegacyValueConverter() {
    }

    public static String parseEnchantment(String materialName) {
        return switch (materialName.toUpperCase(Locale.ROOT)) {
            case "DAMAGE_ALL" -> "SHARPNESS";
            case "ARROW_DAMAGE" -> "POWER";
            case "ARROW_FIRE" -> "FLAME";
            case "ARROW_INFINITE" -> "INFINITY";
            case "ARROW_KNOCKBACK" -> "PUNCH";
            case "DAMAGE_ARTHROPODS" -> "BANE_OF_ARTHROPODS";
            case "DAMAGE_UNDEAD" -> "SMITE";
            case "DIG_SPEED" -> "EFFICIENCY";
            case "DURABILITY" -> "UNBREAKING";
            case "LOOT_BONUS_BLOCKS" -> "FORTUNE";
            case "LOOT_BONUS_MOBS" -> "LOOTING";
            case "LUCK" -> "LUCK_OF_THE_SEA";
            case "RESPIRATION" -> "RESPIRATION";
            case "PROTECTION_ENVIRONMENTAL" -> "PROTECTION";
            case "BLAST_PROTECTION" -> "BLAST_PROTECTION";
            case "PROTECTION_FALL" -> "FEATHER_FALLING";
            case "PROTECTION_FIRE" -> "FIRE_PROTECTION";
            case "PROTECTION_PROJECTILE" -> "PROJECTILE_PROTECTION";
            case "WATER_WORKER" -> "AQUA_AFFINITY";
            case "CURSE_OF_VANISHING" -> "VANISHING_CURSE";
            case "CURSE_OF_BINDING" -> "BINDING_CURSE";
            case "WATER_AFFINITY" -> "AQUA_AFFINITY";
            default -> materialName;
        };
    }

    public static String parsePotionEffect(String potionEffectName) {
        return switch (potionEffectName.toUpperCase(Locale.ROOT)) {
            case "CONFUSION" -> "NAUSEA";
            case "DAMAGE_RESISTANCE" -> "RESISTANCE";
            case "FAST_DIGGING" -> "HASTE";
            case "HARM" -> "INSTANT_DAMAGE";
            case "HEAL" -> "INSTANT_HEALTH";
            case "INCREASE_DAMAGE" -> "STRENGTH";
            case "JUMP" -> "JUMP_BOOST";
            case "SLOW" -> "SLOWNESS";
            case "SLOW_DIGGING" -> "MINING_FATIGUE";
            default -> potionEffectName;
        };
    }

    public static String parseParticle(String potionEffectName) {
        return switch (potionEffectName.toUpperCase(Locale.ROOT)) {
            case "EXPLOSION_NORMAL", "EXPLOSION_LARGE" -> Particle.EXPLOSION.toString();
            case "SMOKE_NORMAL" -> Particle.SMOKE.toString();
            case "SMOKE_LARGE" -> Particle.LARGE_SMOKE.toString();
            case "REDSTONE" -> Particle.DUST.toString();
            case "SLIME" -> Particle.ITEM_SLIME.toString();
            case "DRIP_LAVA" -> Particle.DRIPPING_WATER.toString();
            case "EXPLOSION_HUGE" -> Particle.EXPLOSION.toString();
            case "SNOWBALL" -> Particle.SNOWFLAKE.toString();
            case "SPELL" -> Particle.WITCH.toString();
            case "DRIP_WATER" -> Particle.DRIPPING_WATER.toString();
            case "SPELL_MOB" -> Particle.WITCH.toString();
            case "VILLAGER_ANGRY" -> Particle.ANGRY_VILLAGER.toString();
            case "WATER_BUBBLE" -> Particle.UNDERWATER.toString();
            case "VILLAGER_HAPPY" -> Particle.HAPPY_VILLAGER.toString();
            case "WATER_SPLASH" -> Particle.SPLASH.toString();
            default -> potionEffectName;
        };
    }

    public static String parseDeserializedBlocks(String originalDeserializedBlock) {
        if (originalDeserializedBlock.endsWith("grass"))
            return originalDeserializedBlock.replace("grass", "grass_block[snowy=false]");
        if (!VersionChecker.serverVersionOlderThan(21,9) && originalDeserializedBlock.contains("minecraft:chain"))
            return originalDeserializedBlock.replace("minecraft:chain", "iron_chain");
        return originalDeserializedBlock;
    }

    /**
     * Converts legacy material names to their new equivalents.
     * In 1.21.9+, CHAIN was renamed to IRON_CHAIN.
     */
    public static String parseMaterial(String materialName) {
        if (materialName == null) return null;
        if (!VersionChecker.serverVersionOlderThan(21, 9)) {
            if ("CHAIN".equalsIgnoreCase(materialName) || "CHAIN_BLOCK".equalsIgnoreCase(materialName))
                return "IRON_CHAIN";
        } else {
            if ("IRON_CHAIN".equalsIgnoreCase(materialName) || "CHAIN_BLOCK".equalsIgnoreCase(materialName))
                return "CHAIN";
        }
        return materialName;
    }
}
