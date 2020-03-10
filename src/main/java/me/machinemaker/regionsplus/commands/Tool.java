package me.machinemaker.regionsplus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.machinemaker.regionsplus.misc.Lang;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Tool extends BaseCommand {

    private ItemStack tool;

    public Tool() {
        tool = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = tool.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&l❱&5&l❱ &eSelection Tool &5&l❰&d&l❰"));
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Right/Left click with this", ChatColor.GRAY + "tool to set a position for", ChatColor.GRAY + "defining a region"));
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        tool.setItemMeta(meta);
    }


    @CommandAlias("tool|t")
    @CommandPermission("regionsplus.tool")
    public void tool(Player p) {
        p.getInventory().addItem(tool);
        p.sendMessage(Lang.TOOL_RECEIVED.p());
    }
}
