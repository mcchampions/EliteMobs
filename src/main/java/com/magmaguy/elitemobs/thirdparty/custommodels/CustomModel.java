package com.magmaguy.elitemobs.thirdparty.custommodels;

import com.magmaguy.elitemobs.api.EliteMobDeathEvent;
import com.magmaguy.elitemobs.entitytracker.EntityTracker;
import com.magmaguy.elitemobs.mobconstructor.EliteEntity;
import com.magmaguy.elitemobs.mobconstructor.custombosses.CustomBossEntity;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;


public class CustomModel implements CustomModelInterface {
    @Getter
    private static final boolean usingModels = false;
    @Getter
    private static ModelPlugin modelPlugin;
    private boolean initialized;

    private CustomModel(LivingEntity livingEntity, String modelName, String nametagName) {}


    public static void initialize() {
        modelPlugin = ModelPlugin.NONE;
    }

    public static void reloadModels() {}

    public static boolean modelExists(String modelName) {
        return false;
    }

    public static CustomModel generateCustomModel(LivingEntity livingEntity, String modelName, String nametagName) {
        CustomModel customModel = new CustomModel(livingEntity, modelName, nametagName);
        return customModel.initialized ? customModel : null;
    }

    public static CustomModel generateCustomModel(LivingEntity livingEntity, String modelName, String nametagName,
                                                   ModeledEntityLeftClickCallback leftClickCallback,
                                                   ModeledEntityRightClickCallback rightClickCallback) {
        return generateCustomModel(livingEntity, modelName, nametagName);
    }

    public static boolean customModelsEnabled() {
        return modelPlugin != ModelPlugin.NONE;
    }

    @Override
    public void shoot() {}

    @Override
    public void melee() {}

    @Override
    public void playAnimationByName(String animationName) {}

    @Override
    public void setName(String nametagName, boolean visible) { }

    @Override
    public void setNameVisible(boolean visible) { }

    @Override
    public void addPassenger(CustomBossEntity passenger) {}

    @Override
    public void switchPhase() {}

    @Override
    public Location getNametagBoneLocation() {
        return null;
    }

    @Override
    public void setSyncMovement(boolean syncMovement) {}

    public enum ModelPlugin {
        NONE, FREE_MINECRAFT_MODELS, MODEL_ENGINE
    }

    public static class ModelEntityEvents implements Listener {
        @EventHandler(ignoreCancelled = true)
        public void onMeleeHit(EntityDamageByEntityEvent event) {
            EliteEntity eliteEntity = EntityTracker.getEliteMobEntity(event.getDamager());
            if (!(eliteEntity instanceof CustomBossEntity)) return;
            if (((CustomBossEntity) eliteEntity).getCustomModel() == null) return;
            ((CustomBossEntity) eliteEntity).getCustomModel().melee();
        }

        @EventHandler(ignoreCancelled = true)
        public void onRangedShot(EntitySpawnEvent event) {
            if (!(event.getEntity() instanceof Projectile)) return;
            if (!(((Projectile) event.getEntity()).getShooter() instanceof LivingEntity)) return;
            EliteEntity eliteEntity = EntityTracker.getEliteMobEntity((LivingEntity) ((Projectile) event.getEntity()).getShooter());
            if (!(eliteEntity instanceof CustomBossEntity)) return;
            if (((CustomBossEntity) eliteEntity).getCustomModel() == null) return;
            ((CustomBossEntity) eliteEntity).getCustomModel().shoot();
        }

        @EventHandler
        public void onDeathEvent(EliteMobDeathEvent event) {
            if (event.getEliteEntity() instanceof CustomBossEntity customBossEntity && customBossEntity.getCustomModel() != null)
                customBossEntity.getCustomModel().playAnimationByName("death");
        }
    }
}
