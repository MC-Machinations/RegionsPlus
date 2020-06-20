package me.machinemaker.regionsplus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.google.common.base.Enums;
import com.google.inject.Inject;
import me.machinemaker.regionsplus.RegionsPlus.MainConfig;
import me.machinemaker.regionsplus.flags.Flags;
import me.machinemaker.regionsplus.flags.StateFlag;
import me.machinemaker.regionsplus.flags.StateFlag.State;
import me.machinemaker.regionsplus.flags.StringFlag;
import me.machinemaker.regionsplus.misc.Lang;
import me.machinemaker.regionsplus.misc.Sender;
import me.machinemaker.regionsplus.regions.BaseRegion;
import me.machinemaker.regionsplus.regions.Region;
import me.machinemaker.regionsplus.utils.RegionManager;
import me.machinemaker.regionsplus.utils.Util;
import me.machinemaker.regionsplus.worlds.RegionWorld;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.bukkit.ChatColor.*;

@CommandAlias("rg|region")
public class RegionCmd extends BaseCommand {

    @Inject
    private RegionManager regionManager;

    @Inject
    private MainConfig config;

//    @CommandAlias("test")
//    public void test(CommandSender cs, @Optional String value) {
//        if (isNull(value)) {
//            cs.sendMessage(config.useActionBar);
//        } else {
//            config.useActionBar = value;
//            config.save();
//        }
//    }

    @Subcommand("info")
    @CommandPermission("regionsplus.region.info")
    @Description("Shows info about a specific region or the region(s) you are in")
    @CommandCompletion("@regionNames:global=true")
    public void info(CommandSender cs, @Optional BaseRegion region) {
        if (cs instanceof ConsoleCommandSender && isNull(region))
            cs.sendMessage(Lang.NO_WORLD_CONSOLE.err());
        else if (!isNull(region))
            region.getRegionInfo().forEach(cs::sendMessage);
        else {
            RegionWorld rgWorld = regionManager.getWorld(((Player) cs).getWorld().getName());
            BaseRegion rgSelect = rgWorld.getRegions().stream().filter(rg -> rg.inRegion(((Player) cs).getLocation())).min(Comparator.comparingLong(Region::getVolume)).map(rg -> (BaseRegion) rg).orElse(rgWorld.getGlobal());
            rgSelect.getRegionInfo().forEach(cs::sendMessage);
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    @Subcommand("flag")
    public class FlagRegionCmd extends BaseCommand {
        @Subcommand("set")
        @CommandPermission("regionsplus.region.flag.add")
        @Description("Sets a flag to a region")
        @CommandCompletion("@regionNames:global=true * @flaginput")
        public void setFlag(Sender cs, BaseRegion region, Flags flag, @Default() String input) {
            switch (flag.getType()) {
                case STATE: {
                    StateFlag sFlag = Enums.getIfPresent(StateFlag.class, flag.u()).orNull();
                    State state = Enums.getIfPresent(State.class, input.toUpperCase()).orNull();
                    if (isNull(sFlag) || isNull(state)) {
                        cs.sendMessage("invalid"); //TODO Lang
                        return;
                    }
                    if (!isNull(region.getStateFlag(sFlag)) && region.getStateFlag(sFlag).getValue() == state) {
                        cs.sendMessage("SAME"); //TODO Lang
                        return;
                    } else {
                        if (cs.isPlayer()) Util.clearChat(cs.getSender());
                        region.setStateFlag(sFlag, state);
                        if (state == State.ALLOW)
                            cs.sendActionMsg(Lang.FLAG_ALLOW_SET.toString().replace("{flag}", sFlag.toUpper()).replace("{region}", region.getName()));
                        else
                            cs.sendActionMsg(Lang.FLAG_DENY_SET.toString().replace("{flag}", sFlag.toUpper()).replace("{region}", region.getName()));
                    }
                    break;
                }
                case STRING: {
                    StringFlag sFlag = Enums.getIfPresent(StringFlag.class, flag.u()).orNull();
                    if (isNull(sFlag)) {
                        cs.sendMessage("invalid"); //TODO Lang
                        return;
                    }
                    if (!isNull(region.getStringFlag(sFlag)) && region.getStringFlag(sFlag).getValue().equals(input)) {
                        cs.sendMessage("SAME"); //TODO Lang
                        return;
                    } else {
                        if (cs.isPlayer()) Util.clearChat(cs.getSender());
                        region.setStringFlag(sFlag, input); //TODO Lang
                        region.formatFlags(1).forEach(cs::sendMessage);
                    }
                    break;
                }
            }
            if (cs.isPlayer()) region.formatFlags((int)Math.ceil((flag.index + 1) / 18.0)).forEach(cs::sendMessage);
        }

        @Subcommand("list")
        @CommandAlias("flags")
        @CommandPermission("regionsplus.region.flag.list")
        @Description("Lists flags for a region")
        @CommandCompletion("@regionNames:global=true")
        public void listFlags(CommandSender cs, @Default("$&CURRENT&$") BaseRegion region, @Default("1") int page) {
            region.formatFlags(page).forEach(cs::sendMessage);
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    @Subcommand("admin")
    public class AdminRegionCmd extends BaseCommand {
        @Subcommand("add")
        @CommandPermission("regionsplus.region.admin.add")
        @Description("Adds admin player(s) (separate with ,)")
        @CommandCompletion("@regionNames:global=true @allplayersarray:region=true,admin=false")
        public void addAdmin(CommandSender cs, BaseRegion region, OfflinePlayer...players) {
            if (cs instanceof Player) Util.clearChat(cs);
            //noinspection ConstantConditions
            region.addAdmins(
                    Arrays.stream(players)
                            .filter(player -> {
                                if (region.hasAdmin(player.getUniqueId()))
                                    cs.sendMessage(Lang.PLAYER_ALREADY.p().replace("{name}", player.getName()).replace("{type}", "admin").replace("{region}", region.getName()));
                                return !region.hasAdmin(player.getUniqueId());
                            })
                            .peek(player -> cs.sendMessage(Lang.PLAYER_ADDED.p().replace("{name}", player.getName()).replace("{type}", "admin").replace("{region}", region.getName())))
                            .map(OfflinePlayer::getUniqueId).collect(Collectors.toList())
            );
            region.getRegionInfo().forEach(cs::sendMessage);
        }

        @Subcommand("del")
        @CommandPermission("regionsplus.region.admin.delete")
        @Description("Deletes an admin player")
        @CommandCompletion("@regionNames:global=true @allplayersarray:region=true,admin=true")
        public void delAdmin(CommandSender cs, BaseRegion region, OfflinePlayer...players) {
            if (cs instanceof Player) Util.clearChat(cs);
            //noinspection ConstantConditions
            region.removeAdmins(
                    Arrays.stream(players)
                            .filter(player -> {
                                if (!region.hasAdmin(player.getUniqueId()))
                                    cs.sendMessage(Lang.PLAYER_NOT.p().replace("{name}", player.getName()).replace("{type}", "admin").replace("{region}", region.getName()));
                                return region.hasAdmin(player.getUniqueId());
                            })
                            .peek(player -> cs.sendMessage(Lang.PLAYER_REMOVED.p().replace("{name}", player.getName()).replace("{type}", "admin").replace("{region}", region.getName())))
                            .map(OfflinePlayer::getUniqueId).collect(Collectors.toList())
            );
            region.getRegionInfo().forEach(cs::sendMessage);
        }

        @Subcommand("list")
        @CommandPermission("regionsplus.region.admin.list")
        @Description("Lists the admin players")
        @CommandCompletion("@regionNames:global=true")
        public void listAdmins(CommandSender cs, @Default("$&CURRENT&$") BaseRegion region) {
            String playerList = region.getAdmins().size() == 0 ? RED.toString() + ITALIC + "(none)" : region.getAdmins().stream().map(uuid -> Bukkit.getOfflinePlayer(uuid).getName()).collect(Collectors.joining(GRAY + ", " + GOLD));
            cs.sendMessage(Lang.LIST_PLAYERS.p().replace("{region}", region.getName()).replace("{type}", "Admins").replace("{players}", playerList));
        }

        @Subcommand("clear")
        @CommandPermission("regionsplus.region.admin.clear")
        @Description("Clears the admins for the region")
        @CommandCompletion("@regionNames:global=true")
        public void clearAdmins(CommandSender cs, BaseRegion region) {
            if (cs instanceof Player) Util.clearChat(cs);
            cs.sendMessage(Lang.CLEARED_PLAYERS.p().replace("{region}", region.getName()).replace("{type}", "Admin"));
            region.removeAdmins(region.getAdmins());
            region.getRegionInfo().forEach(cs::sendMessage);
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    @Subcommand("user")
    public class UserRegionCmd extends BaseCommand {
        @Subcommand("add")
        @CommandPermission("regionsplus.region.user.add")
        @Description("Adds a user player")
        @CommandCompletion("@regionNames:global=true @allplayersarray:region=true,user=false")
        public void addUser(CommandSender cs, BaseRegion region, OfflinePlayer...players) {
            if (cs instanceof Player) Util.clearChat(cs);
            //noinspection ConstantConditions
            region.addUsers(
                    Arrays.stream(players)
                            .filter(player -> {
                                if (region.hasUser(player.getUniqueId()))
                                    cs.sendMessage(Lang.PLAYER_ALREADY.p().replace("{name}", player.getName()).replace("{type}", "user").replace("{region}", region.getName()));
                                return !region.hasUser(player.getUniqueId());
                            })
                            .peek(player -> cs.sendMessage(Lang.PLAYER_ADDED.p().replace("{name}", player.getName()).replace("{type}", "user").replace("{region}", region.getName())))
                            .map(OfflinePlayer::getUniqueId).collect(Collectors.toList())
            );
            region.getRegionInfo().forEach(cs::sendMessage);
        }

        @Subcommand("del")
        @CommandPermission("regionsplus.region.user.delete")
        @Description("Deletes a user player")
        @CommandCompletion("@regionNames:global=true @allplayersarray:region=true,user=true")
        public void delUser(CommandSender cs, BaseRegion region, OfflinePlayer...players) {
            if (cs instanceof Player) Util.clearChat(cs);
            //noinspection ConstantConditions
            region.removeUsers(
                    Arrays.stream(players)
                            .filter(player -> {
                                if (!region.hasUser(player.getUniqueId()))
                                    cs.sendMessage(Lang.PLAYER_NOT.p().replace("{name}", player.getName()).replace("{type}", "user").replace("{region}", region.getName()));
                                return region.hasUser(player.getUniqueId());
                            })
                            .peek(player -> cs.sendMessage(Lang.PLAYER_REMOVED.p().replace("{name}", player.getName()).replace("{type}", "user").replace("{region}", region.getName())))
                            .map(OfflinePlayer::getUniqueId).collect(Collectors.toList())
            );
            region.getRegionInfo().forEach(cs::sendMessage);
        }

        @Subcommand("list")
        @CommandPermission("regionsplus.region.user.list")
        @Description("Lists the user players")
        @CommandCompletion("@regionNames:global=true")
        public void listUsers(CommandSender cs, @Default("$&CURRENT&$") BaseRegion region) {
            String playerList = region.getUsers().size() == 0 ? RED.toString() + ITALIC + "(none)" : region.getUsers().stream().map(uuid -> Bukkit.getOfflinePlayer(uuid).getName()).collect(Collectors.joining(GRAY + ", " + GOLD));
            cs.sendMessage(Lang.LIST_PLAYERS.p().replace("{region}", region.getName()).replace("{type}", "Users").replace("{players}", playerList));
        }

        @Subcommand("clear")
        @CommandPermission("regionsplus.region.user.clear")
        @Description("Clears the users for the region")
        @CommandCompletion("@regionNames:global=true")
        public void clearUsers(CommandSender cs, BaseRegion region) {
            if (cs instanceof Player) Util.clearChat(cs);
            cs.sendMessage(Lang.CLEARED_PLAYERS.p().replace("{region}", region.getName()).replace("{type}", "User"));
            region.removeUsers(region.getUsers());
            region.getRegionInfo().forEach(cs::sendMessage);
        }
    }

    @HelpCommand
    @Description("Help command for RegionsPlus commands")
    public void onHelp(CommandSender __, CommandHelp help) {
        help.showHelp(); //TODO: Format help
    }
}
