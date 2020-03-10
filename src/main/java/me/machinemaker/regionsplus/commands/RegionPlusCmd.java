package me.machinemaker.regionsplus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.machinemaker.regionsplus.misc.Lang;
import me.machinemaker.regionsplus.RegionManager;
import me.machinemaker.regionsplus.SelectionManager;
import me.machinemaker.regionsplus.regions.Region;
import me.machinemaker.regionsplus.regions.RegionType;
import me.machinemaker.regionsplus.worlds.RegionWorld;
import org.bukkit.entity.Player;

import java.util.Collections;

@CommandAlias("regionsplus|regionsp|rplus|rp")
public class RegionPlusCmd extends BaseCommand {

    @Subcommand("create|add") //TODO Move to other command
    @CommandPermission("regionsplus.region.create")
    @Conditions("selection")
    @Description("Creates a region with the selected volume")
    public void create(Player p, String name, @Default("CUBE") @Values("@regionTypes") RegionType type) {
        if (type == RegionType.GLOBAL)
            p.sendMessage(Lang.NO_CREATE_GLOBAL.err());
        RegionWorld world = RegionManager.get().getWorld(p.getWorld().getName());
        if (world.hasRegion(name))
            p.sendMessage(Lang.ALREADY_REGION.err().replace("{name}", name));
        Region region = world.createRegion(name, type, SelectionManager.get().getSelection(p));
        region.addAdmins(Collections.singleton(p.getUniqueId()));
        p.sendMessage(Lang.CREATED_REGION.p().replace("{name}", name).replace("{world}", world.getName()));
    }

    @Subcommand("delete|del")
    @CommandPermission("regionsplus.region.delete")
    @Description("Deletes the specified region")
    @CommandCompletion("@regionNames:global=false")
    public void delete(Player p, Region region) {
        RegionWorld world = RegionManager.get().getWorld(p.getWorld().getName());
        if (world.removeRegion(region))
            p.sendMessage(Lang.DELETED_REGION.p().replace("{name}", region.getName()).replace("{world}", world.getName()));
        else p.sendMessage(Lang.ERROR_DELETING.err().replace("{name}", region.getName()).replace("{world}", world.getName()));
    }



}
