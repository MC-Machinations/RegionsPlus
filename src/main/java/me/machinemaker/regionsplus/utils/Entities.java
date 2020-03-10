package me.machinemaker.regionsplus.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Vehicle;

public class Entities {

    public static boolean isRideable(Entity entity) {
        return entity instanceof Pig ? ((Pig) entity).hasSaddle() : entity instanceof Vehicle;
    }
}
