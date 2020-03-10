package me.machinemaker.regionsplus.tool;

import me.machinemaker.regionsplus.SelectionManager;
import me.machinemaker.regionsplus.misc.Lang;
import me.machinemaker.regionsplus.misc.Util;
import me.machinemaker.regionsplus.regions.Selection;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) { //TODO: Rework
        if (!event.getPlayer().hasPermission("regionsplus.tool") || event.getClickedBlock() == null) return;
        if (event.getItem() == null || event.getItem().getType() != Material.BLAZE_ROD || !event.getItem().containsEnchantment(Enchantment.ARROW_INFINITE)) return; //TODO: Better checks
        Location loc = event.getClickedBlock().getLocation();
        Selection selection = SelectionManager.get().getSelection(event.getPlayer());
        event.setCancelled(true);
        if (selection.getWorld() != null && selection.getWorld().getUID() != loc.getWorld().getUID()) selection.clear();
        ComponentBuilder string = new ComponentBuilder(Lang.PREFIX.toString()).append("Position ").color(ChatColor.AQUA).bold(true);
        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK:
                if (selection.getWorld() != null && selection.getWorld().getUID().equals(loc.getWorld().getUID()) && selection.getPos1() != null && selection.getPos1().distanceSquared(loc) == 0) return;
                selection.setPos1(loc);
                string.append("#1").color(ChatColor.AQUA).bold(false);
                break;
            case RIGHT_CLICK_BLOCK:
                if (selection.getWorld() != null && selection.getWorld().getUID().equals(loc.getWorld().getUID()) && selection.getPos2() != null && selection.getPos2().distanceSquared(loc) == 0) return;
                selection.setPos2(loc);
                string.append("#2").color(ChatColor.AQUA).bold(false);
                break;
        }
        string.append(": Set to ").color(ChatColor.GRAY).append(Util.getLocComponent(loc));
        if (selection.isComplete()) {
            string.append("[Create]").color(ChatColor.GREEN)
                    .event(new ClickEvent(Action.SUGGEST_COMMAND,  "/rplus create "))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to create a new region with the selection").color(ChatColor.GRAY).create()));
        }
        event.getPlayer().sendMessage(string.create());
    }
}
