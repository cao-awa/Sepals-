package com.github.cao.awa.sepals;

import com.github.cao.awa.sepals.backup.SepalsBackupCenter;
import com.github.cao.awa.sepals.command.SepalsBackupCommand;
import com.github.cao.awa.sepals.command.SepalsConfigCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sepals implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Sepals");
    public static SepalsBackupCenter backupCenter;
    public static boolean enableSepalsVillager = true;
    public static boolean enableSepalsFrogLookAt = true;
    public static boolean enableSepalsFrogAttackableSensor = true;
    public static boolean enableSepalsLivingTargetCache = true;
    public static boolean nearestLivingEntitiesSensorUseQuickSort = true;
    public static boolean enableSepalsBiasedJumpLongTask = true;
    public static boolean enableSepalsWeightTable = false;
    public static boolean enableEntitiesCramming = false;

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(SepalsConfigCommand::register);
        ServerLifecycleEvents.SERVER_STARTING.register(SepalsBackupCommand::register);
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            try {
                backupCenter = SepalsBackupCenter.fromServer(server);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

// /spark profiler start --timeout 60