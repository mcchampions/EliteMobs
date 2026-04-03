package com.magmaguy.elitemobs.wormhole;

import com.magmaguy.elitemobs.config.wormholes.WormholeConfigFields;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Wormhole {
    @Getter
    private static final HashSet<Wormhole> wormholes = new HashSet<>();

    @Getter
    private final WormholeConfigFields wormholeConfigFields;
    @Getter
    private final WormholeEntry wormholeEntry1;
    @Getter
    private final WormholeEntry wormholeEntry2;
    @Getter
    private final Color particleColor;
    @Getter
    private List<List<Vector>> cachedRotations = new ArrayList<>();


    public Wormhole(WormholeConfigFields wormholeConfigFields) {
        this.wormholeConfigFields = wormholeConfigFields;
        this.particleColor = Color.fromRGB(wormholeConfigFields.getParticleColor());
        if (wormholeConfigFields.getStyle() != null && wormholeConfigFields.getStyle() != WormholeStyle.NONE) {
            this.cachedRotations = new ArrayList<>(new VisualEffects(wormholeConfigFields).getCachedRotations());
        }
        wormholeEntry1 = new WormholeEntry(this, this.wormholeConfigFields.getLocation1(), 1);
        wormholeEntry2 = new WormholeEntry(this, this.wormholeConfigFields.getLocation2(), 2);
        wormholes.add(this);

        // Make sure the manager is initialized
        WormholeManager.getInstance(false);
    }

    public static void shutdown() {
        // Shutdown the centralized manager
        WormholeManager.getInstance(true).shutdown();

        // Clean up wormhole entries
        for (Wormhole wormhole : wormholes) {
            wormhole.stop();
        }

        wormholes.clear();
        WormholeEntry.getWormholeEntries().clear();

        // Clean up player listener tracking
        WormholePlayerListener.shutdown();
    }

    private void stop() {
        wormholeEntry1.stop();
        wormholeEntry2.stop();
    }

    public void onDungeonInstall(String dungeonFilename) {
        if (wormholeEntry1 != null &&
            wormholeEntry1.getLocationString() != null &&
            wormholeEntry1.getLocationString().equals(dungeonFilename)) {
            wormholeEntry1.onDungeonInstall();
        } else if (wormholeEntry2 != null &&
                   wormholeEntry2.getLocationString() != null &&
                   wormholeEntry2.getLocationString().equals(dungeonFilename)) {
            wormholeEntry2.onDungeonInstall();
        }
    }

    public void onDungeonUninstall(String dungeonFilename) {
        if (wormholeEntry1.getWormhole() != null && wormholeEntry1.getLocationString().equals(dungeonFilename)) {
            wormholeEntry1.onDungeonUninstall();
        } else if (wormholeEntry2.getWormhole() != null && wormholeEntry2.getLocationString().equals(dungeonFilename)) {
            wormholeEntry2.onDungeonUninstall();
        }
    }

    public enum WormholeStyle {
        NONE,
        CRYSTAL,
        ICOSAHEDRON,
        CUBE
    }
}