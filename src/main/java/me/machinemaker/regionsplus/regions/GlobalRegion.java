package me.machinemaker.regionsplus.regions;

import me.machinemaker.regionsplus.worlds.RegionWorld;
import org.bukkit.Location;

public class GlobalRegion extends BaseRegion {

    public GlobalRegion(RegionWorld world) {
        super(world, "__global__", RegionType.GLOBAL);
        //TODO: Global stuff
    }

    @Override
    public boolean inRegion(Location loc) {
        return this.world.getWorldUID().equals(loc.getWorld().getUID());
    }

    @Override
    public long getVolume() {
        return Long.MAX_VALUE;
    }
}
