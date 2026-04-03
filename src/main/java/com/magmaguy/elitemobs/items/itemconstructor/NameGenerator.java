package com.magmaguy.elitemobs.items.itemconstructor;

import com.magmaguy.elitemobs.config.StaticItemNamesConfig;
import com.magmaguy.magmacore.util.ChatColorConverter;
import com.magmaguy.magmacore.util.Logger;
import org.bukkit.Material;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NameGenerator {

    private NameGenerator() {
    }

    public static String generateName(String rawName) {
        return ChatColorConverter.convert(rawName);
    }

    public static String generateName(Material material) {
        List<String> names = getNameListForMaterial(material);
        if (names == null || names.isEmpty()) {
            Logger.warn("Found unexpected material type in procedurally generated loot. Can't generate item name.");
            Logger.warn("Material name: " + material);
            return "";
        }
        return names.get(ThreadLocalRandom.current().nextInt(names.size()));
    }

    private static List<String> getNameListForMaterial(Material material) {
        return switch (material) {
            case DIAMOND_SWORD, GOLDEN_SWORD, IRON_SWORD, STONE_SWORD, WOODEN_SWORD ->
                    StaticItemNamesConfig.getSwordNames();
            case BOW -> StaticItemNamesConfig.getBowNames();
            case DIAMOND_PICKAXE, GOLDEN_PICKAXE, IRON_PICKAXE, STONE_PICKAXE, WOODEN_PICKAXE ->
                    StaticItemNamesConfig.getPickaxeNames();
            case DIAMOND_SHOVEL, GOLDEN_SHOVEL, IRON_SHOVEL, STONE_SHOVEL, WOODEN_SHOVEL ->
                    StaticItemNamesConfig.getShovelNames();
            case DIAMOND_HOE, GOLDEN_HOE, IRON_HOE, STONE_HOE, WOODEN_HOE -> StaticItemNamesConfig.getHoeNames();
            case DIAMOND_AXE, GOLDEN_AXE, IRON_AXE, STONE_AXE, WOODEN_AXE -> StaticItemNamesConfig.getAxeNames();
            case CHAINMAIL_HELMET, DIAMOND_HELMET, GOLDEN_HELMET, IRON_HELMET, LEATHER_HELMET, TURTLE_HELMET ->
                    StaticItemNamesConfig.getHelmetNames();
            case CHAINMAIL_CHESTPLATE, DIAMOND_CHESTPLATE, GOLDEN_CHESTPLATE, IRON_CHESTPLATE, LEATHER_CHESTPLATE ->
                    StaticItemNamesConfig.getChestplateNames();
            case CHAINMAIL_LEGGINGS, DIAMOND_LEGGINGS, GOLDEN_LEGGINGS, IRON_LEGGINGS, LEATHER_LEGGINGS ->
                    StaticItemNamesConfig.getLeggingsNames();
            case CHAINMAIL_BOOTS, DIAMOND_BOOTS, GOLDEN_BOOTS, IRON_BOOTS, LEATHER_BOOTS ->
                    StaticItemNamesConfig.getBootsNames();
            case SHEARS -> StaticItemNamesConfig.getShearsNames();
            case FISHING_ROD -> StaticItemNamesConfig.getFishingRodNames();
            case SHIELD -> StaticItemNamesConfig.getShieldNames();
            case TRIDENT -> StaticItemNamesConfig.getTridentNames();
            case CROSSBOW -> StaticItemNamesConfig.getCrossbowNames();
            case MACE -> StaticItemNamesConfig.getMaceNames();
            case DIAMOND_SPEAR, IRON_SPEAR, GOLDEN_SPEAR, STONE_SPEAR, WOODEN_SPEAR, COPPER_SPEAR, NETHERITE_SPEAR ->
                    StaticItemNamesConfig.getSpearNames();
            default -> null;
        };
    }

}
