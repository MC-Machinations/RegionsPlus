package me.machinemaker.regionsplus;

import me.machinemaker.regionsplus.regions.BaseRegion;
import me.machinemaker.regionsplus.worlds.RegionWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class RegionManager {

    private static RegionManager instance = new RegionManager();
    private RegionManager() { }
    public static RegionManager get() {
        return instance;
    }

    private RegionsPlus plugin;
    private Map<String, RegionWorld> worlds;
    private File regionFolder;

    private Map<String, Long> messages;

    void init(RegionsPlus plugin) {
        this.plugin = plugin;
        messages = new HashMap<>();
        worlds = new HashMap<>();
        regionFolder = new File(plugin.getDataFolder(), "regions");
        if (regionFolder.mkdirs())
            plugin.getLogger().info("Created the regions folder!");
        Bukkit.getWorlds().forEach(world -> {
            File worldFolder = createWorldFolder(world);
            RegionWorld rWorld = new RegionWorld(world, worldFolder);
            worlds.put(world.getName(), rWorld);
        });
    }

    public RegionWorld getWorld(String name) {
        return worlds.get(name);
    }

    public List<BaseRegion> getRegions(Location loc) {
        return getWorld(loc.getWorld().getName()).getRegionsAtLoc(loc);
    }

//    public RegionWorld getWorld(UUID worldUUID) {
//        return worlds.get(worldUUID);
//    }

    public Collection<RegionWorld> getWorlds() {
        return worlds.values();
    }

    private File createWorldFolder(World world) {
        File worldFolder = new File(regionFolder, world.getName());
        if (worldFolder.mkdirs())
            plugin.getLogger().info("Created the region world folder for " + world.getName());
        return worldFolder;
    }

    public void sendMessage(Player p, String msg) {
        if (System.currentTimeMillis() > messages.getOrDefault(p.getUniqueId().toString(), 0L)) {
            p.sendMessage(msg);
            messages.put(p.getUniqueId().toString(), System.currentTimeMillis() + 1000L);
        }
    }
}
