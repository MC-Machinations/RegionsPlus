package me.machinemaker.regionsplus.regions;

import javafx.geometry.Point3D;
import lombok.Getter;
import me.machinemaker.regionsplus.worlds.RegionWorld;
import org.bukkit.Location;

public class CubeRegion extends Region {

    // TODO: Remove dependency on Point3D
    @Getter
    private Point3D dimensions;

    public CubeRegion(RegionWorld world, String name) {
        super(world, name, RegionType.CUBE);
        this.dimensions = new Point3D(Math.abs(pos1.getBlockX()-pos2.getBlockX()), Math.abs(pos1.getBlockY() - pos2.getBlockY()), Math.abs(pos1.getBlockZ() - pos2.getBlockZ()));
    }

    public CubeRegion(RegionWorld world, String name, Location pos1, Location pos2) {
        super(world, name, RegionType.CUBE, pos1, pos2);
        this.dimensions = new Point3D(Math.abs(pos1.getBlockX()-pos2.getBlockX()), Math.abs(pos1.getBlockY() - pos2.getBlockY()), Math.abs(pos1.getBlockZ() - pos2.getBlockZ()));
    }

    @Override
    public boolean inRegion(Location loc) {
        return loc.getBlockX() >= this.pos1.getBlockX() && loc.getBlockX() <= this.pos2.getBlockX()
            && loc.getBlockY() >= this.pos1.getBlockY() && loc.getBlockY() <= this.pos2.getBlockY()
            && loc.getBlockZ() >= this.pos1.getBlockZ() && loc.getBlockZ() <= this.pos2.getBlockZ();
    }

//    public Point3D getDimensions() {
//        return dimensions;
//    }

    @Override
    public long getVolume() {
        return (long) dimensions.getX() * (long) dimensions.getY() * (long) dimensions.getZ();
    }
}
