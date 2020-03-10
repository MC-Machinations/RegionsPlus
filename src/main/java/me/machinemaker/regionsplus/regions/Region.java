package me.machinemaker.regionsplus.regions;

import me.machinemaker.regionsplus.RegionsPlus;
import me.machinemaker.regionsplus.worlds.RegionWorld;
import org.bukkit.Location;

import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class Region extends BaseRegion {

    Location pos1;
    Location pos2;

    Region(RegionWorld world, String name, RegionType type) {
        super(world, name, type);
        this.name = name;
        this.pos1 = (Location) config.get("pos1");
        this.pos2 = (Location) config.get("pos2");
    }

    Region(RegionWorld world, String name, RegionType type, Location pos1, Location pos2) {
        super(world, name, type);
        this.pos1 = new Location(pos1.getWorld(), min(pos1.getBlockX(), pos2.getBlockX()), min(pos1.getBlockY(), pos2.getBlockY()), min(pos1.getBlockZ(), pos2.getBlockZ()));
        this.pos2 = new Location(pos1.getWorld(), max(pos1.getBlockX(), pos2.getBlockX()), max(pos1.getBlockY(), pos2.getBlockY()), max(pos1.getBlockZ(), pos2.getBlockZ()));
        RegionsPlus.newSharedChain(this.taskName)
                .async(() -> {
                    config.set("pos1", this.pos1);
                    config.set("pos2", this.pos2);
                })
                .async(this::saveConfig).execute();
    }
}
