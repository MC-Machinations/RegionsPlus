package me.machinemaker.regionsplus.worlds;

import me.machinemaker.regionsplus.regions.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class RegionWorld {
    private World world;
    private UUID worldUID;
    private File worldFolder;
    private GlobalRegion global;
    private List<Region> regions;

    public RegionWorld(World world, File worldFolder) {
        this.world = world;
        this.worldUID = world.getUID();
        this.worldFolder = worldFolder;
        global = new GlobalRegion(this);
        regions = new ArrayList<>();
        for (File file : Objects.requireNonNull(worldFolder.listFiles((dir, name) -> name.endsWith(".yml") && !name.equals("__global__.yml")))) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            Region region;
            if (config.getString("type").equalsIgnoreCase("cube")) //TODO: NPE check
                region = new CubeRegion(this, file.getName().substring(0, file.getName().lastIndexOf(".yml")));
            else continue;
            regions.add(region);
        }
    }

    public UUID getWorldUID() { return worldUID; }

    public File getWorldFolder() {
        return worldFolder;
    }

    public GlobalRegion getGlobal() {
        return global;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public boolean hasRegion(String name) {
        return regions.stream().anyMatch(region -> region.getName().equals(name));
    }

    public Optional<Region> getRegion(String name) {
        return regions.stream().filter(region -> region.getName().equals(name)).findFirst();
    }

    public Optional<? extends BaseRegion> getRegionAll(String name) {
        return global.getName().equals(name) ? Optional.of(global) : getRegion(name);
    }

    public Region createRegion(String name, RegionType type, Selection selection) {
        Region region = null;
        switch (type) {
            case CUBE:
                region = new CubeRegion(this, name, selection.getPos1(), selection.getPos2());
        }
        regions.add(region);
        return region;
    }

    public boolean removeRegion(Region region) {
        regions.remove(region);
        return region.delete();
    }

    public List<BaseRegion> getAllRegions() {
        List<BaseRegion> regions = new ArrayList<>(this.regions);
        regions.add(global);
        return regions;
    }

    public List<BaseRegion> getRegionsAtLoc(Location loc) {
        return getAllRegions().stream().filter(r -> r.inRegion(loc)).sorted(Comparator.comparingLong(BaseRegion::getVolume).reversed()).collect(Collectors.toList());
    }

    public String getName() {
        return world.getName();
    }
}
