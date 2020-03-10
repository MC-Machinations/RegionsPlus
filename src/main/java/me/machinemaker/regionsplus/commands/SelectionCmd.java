package me.machinemaker.regionsplus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.machinemaker.regionsplus.misc.Lang;
import me.machinemaker.regionsplus.SelectionManager;
import me.machinemaker.regionsplus.misc.Util;
import me.machinemaker.regionsplus.regions.Selection;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("selection|sel")
public class SelectionCmd extends BaseCommand {

    @CommandPermission("regionsplus.info")
    @Subcommand("info")
    @Default
    public void info(Player p) { //TODO: More info
        Selection selection = SelectionManager.get().getSelection(p);
        p.sendMessage(ChatColor.GRAY + "======== " + ChatColor.AQUA + "Selection Info " + ChatColor.GRAY + "========");
        ComponentBuilder world = new ComponentBuilder("• ").color(ChatColor.BLUE).append("World: ").color(ChatColor.GRAY);
        if (selection.getWorld() == null) p.sendMessage(world.append("not set").color(ChatColor.RED).italic(true).create());
        else p.sendMessage(world.append(selection.getWorld().getName()).color(ChatColor.AQUA).create());
        ComponentBuilder pos1 = new ComponentBuilder("• ").color(ChatColor.BLUE).append("Position #1: ").color(ChatColor.GRAY);
        if (selection.getPos1() == null) p.sendMessage(pos1.append("not set").color(ChatColor.RED).italic(true).create());
        else p.sendMessage(pos1.append(Util.getLocComponent(selection.getPos1())).create());
        ComponentBuilder pos2 = new ComponentBuilder("• ").color(ChatColor.BLUE).append("Position #2: ").color(ChatColor.GRAY);
        if (selection.getPos2() == null) p.sendMessage(pos2.append("not set").color(ChatColor.RED).italic(true).create());
        else p.sendMessage(pos2.append(Util.getLocComponent(selection.getPos2())).create());
        p.sendMessage(ChatColor.GRAY + "======== " + ChatColor.AQUA + "Selection Info " + ChatColor.GRAY + "========");
    }

    @Subcommand("tp")
    @Private
    public void tp(Player p, double x, double y, double z) {
        p.sendMessage(Lang.TELEPORTED.p());
        p.teleport(new Location(p.getWorld(), x, y, z, p.getLocation().getYaw(), p.getLocation().getPitch()));
    }

    @Subcommand("spawn")
    @Private
    public void spawn(Player p, World world) {
        p.sendMessage(Lang.TELEPORTED.p());
        p.teleport(world.getSpawnLocation());
    }
}
