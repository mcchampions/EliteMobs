package com.magmaguy.elitemobs.playerdata.statusscreen;

import com.magmaguy.elitemobs.api.utils.EliteItemManager;
import com.magmaguy.elitemobs.config.menus.premade.PlayerStatusMenuConfig;
import com.magmaguy.elitemobs.items.ShareItem;
import com.magmaguy.elitemobs.playerdata.ElitePlayerInventory;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.Set;

public class GearPage {
    protected static TextComponent gearPage(Player targetPlayer) {

        TextComponent textComponent = new TextComponent();

        for (int i = 0; i < 13; i++) {

            TextComponent line;
            if (PlayerStatusMenuConfig.getGearTextLines()[i] == null) continue;
            if (!PlayerStatusMenuConfig.getGearTextLines()[i].contains("{")) {
                line = new TextComponent(parseGearPlaceholders(PlayerStatusMenuConfig.getGearTextLines()[i], targetPlayer) + "\n");
                gearMultiComponentLine(textComponent, line, i, targetPlayer, false, 0);
            } else {
                TextComponent prePlaceholderElements = new TextComponent(parseGearPlaceholders(PlayerStatusMenuConfig.getGearTextLines()[i].split("\\{")[0], targetPlayer));
                gearMultiComponentLine(textComponent, prePlaceholderElements, i, targetPlayer, false, 0);
                for (int j = 0; j < PlayerStatusMenuConfig.getGearTextLines()[i].split("\\{").length; j++) {
                    TextComponent placeholderString = new TextComponent(parseGearPlaceholders(PlayerStatusMenuConfig.getGearTextLines()[i].split("\\{")[j].split("}")[0], targetPlayer));
                    gearMultiComponentLine(textComponent, placeholderString, i, targetPlayer, true, j);
                    if (PlayerStatusMenuConfig.getGearTextLines()[i].split("}").length > j
                            && PlayerStatusMenuConfig.getGearTextLines()[i].split("}")[j].contains("{")) {
                        TextComponent spaceBetweenPlaceholders = new TextComponent(parseGearPlaceholders(PlayerStatusMenuConfig.getGearTextLines()[i].split("}")[j].split("\\{")[0], targetPlayer));
                        gearMultiComponentLine(textComponent, spaceBetweenPlaceholders, i, targetPlayer, false, 0);
                    }
                }
                TextComponent spaceAfterPlaceholders = new TextComponent("\n");
                gearMultiComponentLine(textComponent, spaceAfterPlaceholders, i, targetPlayer, false, 0);
            }


        }

        return textComponent;
    }

    private static String parseGearPlaceholders(String string, Player targetPlayer) {

        if (string.contains("$helmettier"))
            if (targetPlayer.getEquipment().getHelmet() != null)
                return unicodeColorizer(targetPlayer.getEquipment().getHelmet().getType()) +
                        string.replace("$helmettier",
                                ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).helmet.getTier(targetPlayer.getInventory().getHelmet(), true) + "");
            else
                return string.replace("$helmettier",
                        ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).helmet.getTier(targetPlayer.getInventory().getHelmet(), true) + "");
        if (string.contains("$chestplatetier"))
            if (targetPlayer.getEquipment().getChestplate() != null)
                return unicodeColorizer(targetPlayer.getEquipment().getChestplate().getType()) +
                        string.replace("$chestplatetier",
                                ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).chestplate.getTier(targetPlayer.getInventory().getChestplate(), true) + "");
            else
                return string.replace("$chestplatetier",
                        ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).chestplate.getTier(targetPlayer.getInventory().getChestplate(), true) + "");
        if (string.contains("$leggingstier"))
            if (targetPlayer.getEquipment().getLeggings() != null)
                return unicodeColorizer(targetPlayer.getEquipment().getLeggings().getType()) +
                        string.replace("$leggingstier",
                                ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).leggings.getTier(targetPlayer.getInventory().getLeggings(), true) + "");
            else
                return string.replace("$leggingstier",
                        ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).leggings.getTier(targetPlayer.getInventory().getLeggings(), true) + "");
        if (string.contains("$bootstier"))
            if (targetPlayer.getEquipment().getBoots() != null)
                return unicodeColorizer(targetPlayer.getEquipment().getBoots().getType()) +
                        string.replace("$bootstier",
                                ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).boots.getTier(targetPlayer.getInventory().getBoots(), true) + "");
            else
                return string.replace("$bootstier",
                        ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).boots.getTier(targetPlayer.getInventory().getBoots(), true) + "");
        if (string.contains("$mainhandtier"))
            return unicodeColorizer(targetPlayer.getEquipment().getItemInMainHand().getType()) +
                    string.replace("$mainhandtier",
                            ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).mainhand.getTier(targetPlayer.getInventory().getItemInMainHand(), true) + "");
        if (string.contains("$offhandtier"))
            return unicodeColorizer(targetPlayer.getEquipment().getItemInOffHand().getType()) +
                    string.replace("$offhandtier",
                            ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).offhand.getTier(targetPlayer.getInventory().getItemInOffHand(), true) + "");
        if (string.contains("$damage"))
            return string.replace("$damage", ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).baseDamage() + "");
        if (string.contains("$armor"))
            return string.replace("$armor", ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).getEliteDefense(false) + "");
        if (string.contains("$threat"))
            return string.replace("$threat", ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).getNaturalMobSpawnLevel(true) + "");

        return string;

    }

    private static void gearMultiComponentLine(TextComponent textComponent, TextComponent line, int i, Player targetPlayer, boolean brackets, int bracketCount) {

        if (PlayerStatusMenuConfig.getGearHoverLines()[i] != null && !PlayerStatusMenuConfig.getGearHoverLines()[i].isEmpty()) {
            String hoverLines = PlayerStatusMenuConfig.getGearHoverLines()[i];
            if (hoverLines.contains("$helmet"))
                ShareItem.setItemHoverEvent(line, targetPlayer.getInventory().getHelmet());
            else if (hoverLines.contains("$chestplate"))
                ShareItem.setItemHoverEvent(line, targetPlayer.getInventory().getChestplate());
            else if (hoverLines.contains("$leggings"))
                ShareItem.setItemHoverEvent(line, targetPlayer.getEquipment().getLeggings());
            else if (hoverLines.contains("$boots"))
                ShareItem.setItemHoverEvent(line, targetPlayer.getInventory().getBoots());
            else if (brackets) {
                if (hoverLines.contains("{") && hoverLines.contains("}"))
                    for (int j = 0; j < hoverLines.split("\\{").length; j++)
                        if (bracketCount == j) {
                            String parsedLine = hoverLines.split("\\{")[j].split("}")[0];
                            if (parsedLine.contains("$mainhand")) {
                                ShareItem.setItemHoverEvent(line, targetPlayer.getInventory().getItemInMainHand());
                            } else if (parsedLine.contains("$offhand")) {
                                ShareItem.setItemHoverEvent(line, targetPlayer.getInventory().getItemInOffHand());
                            } else
                                PlayerStatusScreen.setHoverText(line, parsedLine);
                        }
            } else if (!(hoverLines.contains("{") && hoverLines.contains("}")))
                PlayerStatusScreen.setHoverText(line, PlayerStatusMenuConfig.getGearHoverLines()[i]);

        }

        textComponent.addExtra(line);
    }

    private static ChatColor unicodeColorizer(Material material) {
        return switch (material) {
            case DIAMOND_AXE, DIAMOND_SWORD, DIAMOND_HOE, DIAMOND_HELMET, DIAMOND_CHESTPLATE, DIAMOND_LEGGINGS,
                 DIAMOND_BOOTS, DIAMOND_SHOVEL, DIAMOND_PICKAXE -> ChatColor.AQUA;
            case IRON_AXE, IRON_SWORD, IRON_HOE, IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS, IRON_SHOVEL,
                 IRON_PICKAXE, CHAINMAIL_HELMET, CHAINMAIL_CHESTPLATE, CHAINMAIL_LEGGINGS, CHAINMAIL_BOOTS, STONE_AXE,
                 STONE_SWORD, STONE_HOE, STONE_SHOVEL, STONE_PICKAXE -> ChatColor.GRAY;
            case GOLDEN_AXE, GOLDEN_SWORD, GOLDEN_HOE, GOLDEN_HELMET, GOLDEN_CHESTPLATE, GOLDEN_LEGGINGS, GOLDEN_BOOTS,
                 GOLDEN_SHOVEL, GOLDEN_PICKAXE -> ChatColor.YELLOW;
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS, WOODEN_AXE, WOODEN_SWORD,
                 WOODEN_HOE, WOODEN_SHOVEL, WOODEN_PICKAXE -> ChatColor.GOLD;
            case TURTLE_HELMET -> ChatColor.GREEN;
            default -> {
                if (material.equals(Material.NETHERITE_HELMET) ||
                    material.equals(Material.NETHERITE_CHESTPLATE) ||
                    material.equals(Material.NETHERITE_LEGGINGS) ||
                    material.equals(Material.NETHERITE_BOOTS) ||
                    material.equals(Material.NETHERITE_SWORD) ||
                    material.equals(Material.NETHERITE_AXE) ||
                    material.equals(Material.NETHERITE_HOE) ||
                    material.equals(Material.NETHERITE_PICKAXE))
                    yield ChatColor.BLACK;
                yield ChatColor.DARK_GRAY;
            }
        };
    }

    protected static void gearPage(Player requestingPlayer, Player targetPlayer) {
        Inventory inventory = Bukkit.createInventory(requestingPlayer, 54, PlayerStatusMenuConfig.getGearChestMenuName());
        //head
        if (EliteItemManager.isEliteMobsItem(targetPlayer.getInventory().getArmorContents()[3]))
            inventory.setItem(11, targetPlayer.getInventory().getArmorContents()[3]);
        //main hand
        if (EliteItemManager.isEliteMobsItem(targetPlayer.getInventory().getItemInMainHand()))
            inventory.setItem(19, targetPlayer.getInventory().getItemInMainHand());
        //chestplate
        if (EliteItemManager.isEliteMobsItem(targetPlayer.getInventory().getArmorContents()[2]))
            inventory.setItem(20, targetPlayer.getInventory().getArmorContents()[2]);
        //shield
        if (EliteItemManager.isEliteMobsItem(targetPlayer.getInventory().getItemInOffHand()))
            inventory.setItem(21, targetPlayer.getInventory().getItemInOffHand());
        //leggings
        if (EliteItemManager.isEliteMobsItem(targetPlayer.getInventory().getArmorContents()[1]))
            inventory.setItem(29, targetPlayer.getInventory().getArmorContents()[1]);
        //boots
        inventory.setItem(38, targetPlayer.getInventory().getArmorContents()[0]);
        inventory.setItem(PlayerStatusMenuConfig.getGearDamageSlot(),
                replaceItemNamePlaceholder(PlayerStatusMenuConfig.getGearDamageItem().clone(), "$damage",
                        ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).baseDamage() + ""));
        inventory.setItem(PlayerStatusMenuConfig.getGearArmorSlot(),
                replaceItemNamePlaceholder(PlayerStatusMenuConfig.getGearArmorItem().clone(), "$defense",
                        ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).getEliteDefense(false) + ""));
        inventory.setItem(PlayerStatusMenuConfig.getGearThreatSlot(),
                replaceItemNamePlaceholder(PlayerStatusMenuConfig.getGearThreatItem().clone(), "$threat",
                        ElitePlayerInventory.playerInventories.get(targetPlayer.getUniqueId()).getNaturalMobSpawnLevel(true) + ""));
        inventory.setItem(53, PlayerStatusMenuConfig.getBackItem());
        GearPageEvents.pageInventories.add(inventory);
        requestingPlayer.openInventory(inventory);
    }

    private static ItemStack replaceItemNamePlaceholder(ItemStack itemStack, String placeholder, String replacement) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace(placeholder, replacement));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static class GearPageEvents implements Listener {
        private static final Set<Inventory> pageInventories = new HashSet<>();

        public static void shutdown() {
            pageInventories.clear();
        }

        @EventHandler(ignoreCancelled = true)
        public void onInventoryInteract(InventoryClickEvent event) {
            Player player = ((Player) event.getWhoClicked()).getPlayer();
            if (!pageInventories.contains(event.getInventory())) return;
            event.setCancelled(true);
            if (event.getSlot() == 53) {
                player.closeInventory();
                CoverPage.coverPage(player);
            }
        }

        @EventHandler
        public void onInventoryClose(InventoryCloseEvent event) {
            pageInventories.remove(event.getInventory());
        }
    }
}
