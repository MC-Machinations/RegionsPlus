package me.machinemaker.regionsplus.utils;

import org.bukkit.entity.*;

public class Entities {

    public static boolean isRideable(Entity entity) {
        return entity instanceof Pig ? ((Pig) entity).hasSaddle() : entity instanceof Vehicle;
    }

    public static boolean isHostile(Entity entity) {
        return entity instanceof Monster
                || entity instanceof EnderDragon
                || entity instanceof Flying
                || entity instanceof Shulker
                || entity instanceof Slime;
    }

    public static boolean isNotHostile(Entity entity) {
        return !isHostile(entity) && entity instanceof Creature;
    }
}
