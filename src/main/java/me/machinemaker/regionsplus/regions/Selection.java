package me.machinemaker.regionsplus.regions;

import org.bukkit.Location;
import org.bukkit.World;

import static java.util.Objects.isNull;

public class Selection {

    private Location pos1;
    private Location pos2;

    public Selection(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public Selection() {
        this.pos1 = null;
        this.pos2 = null;
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public boolean isComplete() {
        return !isNull(this.pos1) && !isNull(this.pos2);
    }

    public void clear() {
        this.pos1 = null;
        this.pos2 = null;
    }

    public World getWorld() {
        if (pos1 != null) return pos1.getWorld();
        if (pos2 != null) return pos2.getWorld();
        return null;
    }
}
