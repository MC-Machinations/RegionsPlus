package me.machinemaker.regionsplus.misc;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder.Joiner;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class Util {

    public static BaseComponent getLocComponent(Location loc) {
        ComponentBuilder builder = new ComponentBuilder("(").color(ChatColor.DARK_AQUA).bold(true)
                .append(""+loc.getBlockX()).color(ChatColor.AQUA).bold(false).append(", ").color(ChatColor.GRAY)
                .append(""+loc.getBlockY()).color(ChatColor.AQUA).bold(false).append(", ").color(ChatColor.GRAY)
                .append(""+loc.getBlockZ()).color(ChatColor.AQUA).bold(false).append(")").color(ChatColor.DARK_AQUA).bold(true);
        BaseComponent component = new TextComponent(builder.create());
        component.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/sel tp " + loc.getX() + " " + (loc.getY()+1) + " " + loc.getZ()));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to teleport").color(ChatColor.AQUA).create()));
        return component;
    }

    public static void clearChat(CommandSender cs) {
        cs.sendMessage(StringUtils.repeat(" \n", 256));
    }
}
