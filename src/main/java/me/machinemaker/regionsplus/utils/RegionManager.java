package me.machinemaker.regionsplus.utils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.machinemaker.regionsplus.RegionsPlus;
import me.machinemaker.regionsplus.RegionsPlus.MainConfig;
import me.machinemaker.regionsplus.regions.BaseRegion;
import me.machinemaker.regionsplus.worlds.RegionWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Singleton
public class RegionManager {

    private RegionsPlus plugin;

    @Inject
    private MainConfig config;

    private Map<String, RegionWorld> worlds;
    private File regionFolder;
    private Map<String, Long> messages;

    public RegionManager(RegionsPlus regionsPlus) {
        this.plugin = regionsPlus;
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

    /**
     * Gets a list of regions the location is in
     * @param loc the {@link Location} to check
     * @return List of {@link BaseRegion}
     */
    public List<BaseRegion> getRegions(@NotNull Location loc) {
        return getWorld(loc.getWorld().getName()).getRegionsAtLoc(loc);
    }

    /**
     * Gets a list of regions where the location is in AND the player is NOT an admin
     * @param loc {@link Location} to check
     * @param player {@link Player} to check
     * @return {@link List<BaseRegion>} list of {@link BaseRegion}s
     */
    public List<BaseRegion> getRegions(Location loc, @Nullable Player player) {
        return getRegions(loc).stream().filter(r -> {
            if (nonNull(player)) return !r.hasAdmin(player.getUniqueId());
            return true;
        }).collect(Collectors.toList());
    }

//    public RegionWorld getWorld(UUID worldUUID) {
//        return worlds.get(worldUUID);
//    }

    public Collection<RegionWorld> getWorlds() {
        return worlds.values();
    }

    private @NotNull File createWorldFolder(@NotNull World world) {
        File worldFolder = new File(regionFolder, world.getName());
        if (worldFolder.mkdirs())
            plugin.getLogger().info("Created the region world folder for " + world.getName());
        return worldFolder;
    }

    public void sendMessage(@NotNull Player p, String msg) {
        if (System.currentTimeMillis() > messages.getOrDefault(p.getUniqueId().toString(), 0L)) {
            if (config.useActionBar) {
                p.sendActionBar(Util.parseColor(msg));
            } else p.sendMessage(Util.parseColor(msg));

            messages.put(p.getUniqueId().toString(), System.currentTimeMillis() + config.msgDelay);
        }
    }
}
