package me.machinemaker.regionsplus.misc;

import me.machinemaker.regionsplus.RegionsPlus;
import me.machinemaker.regionsplus.utils.Util;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public enum Lang {
    PREFIX("&7&l[&dRegions&5&l+&7&l] "),

    // ACF
    ERROR_PREFIX(PREFIX.msg + "&cError: {message}", "acf-core.error_prefix"),
    NO_CONSOLE(PREFIX.msg + "&cError: Console may not execute this command.", "acf-core.not_allowed_on_console"),
    INVALID_SYNTAX(PREFIX.msg + "&7Usage: &b&l{command} &b{syntax}", "acf-core.invalid_syntax"),
    SPECIFY_ONE_OF(PREFIX.msg + "&cError: Please specify one of (&b{valid}&c).", "acf-core.please_specify_one_of"),

    NO_SELECTION("&cYou have not made a selection."),
    PARTIAL_SELECTION("&cYou have not completed a selection."),
    ALREADY_REGION("&b{name} &cis already a region!"),
    INVALID_REGION_TYPE("&b{type} &cis not a valid type. Use one of &b{types}&c."),
    NO_CREATE_GLOBAL("&cYou cannot create a &bGLOBAL &cregion."),
    CREATED_REGION("&aCreated &7region &b{name} &7in &b{world}&7."),
    DELETED_REGION("&cDeleted &7region &b{name} &7in &b{world}&7."),
    ERROR_DELETING("&cCould not delete &b{name}&c in &b{world}&c."),

    REGION_PREFIX("&b{region}&7&l❱ "),
    NOT_VALID_REGION("&cThat region is invalid or &e{world} &cdoes not contain that region!"),
    NO_WORLD_CONSOLE("&cYou must specify a world/region if running this from the &bconsole&c."),
    NOT_VALID_WORLD("&e{world} is not valid!"),
    PLAYER_ALREADY(REGION_PREFIX.msg + "&6{name} &7is already an &9{type}&7."),
    PLAYER_NOT(REGION_PREFIX.msg + "&6{name} &7is not a(n) &9{type}&7."),
    PLAYER_ADDED(REGION_PREFIX.msg + "&6{name} &7was &aadded &7as &9{type}&7."),
    PLAYER_REMOVED(REGION_PREFIX.msg + "&6{name} &7was &cremoved &7as &9{type}&7."),
    FLAG_DENY_SET(REGION_PREFIX.msg + "&6&l{flag} &7was set to &c&lDENY&7."),
    FLAG_ALLOW_SET(REGION_PREFIX.msg + "&6&l{flag} &7was set to &a&lALLOW&7."),

    LIST_PLAYERS(REGION_PREFIX.msg + "&9{type}&7: &6{players}&7."),
    CLEARED_PLAYERS(REGION_PREFIX.msg + "&7All &9{type} &7players have been &acleared&7."),

    ILLEGAL_PREFIX("&c&lHey! "),
    ILLEGAL_ACTION("&7You cannot do that here."),

    TELEPORTED("&bTeleported..."),
    TOOL_RECEIVED("&7You have been given the &b&lselection tool&7!");

    String path;
    String msg;

    Lang(String msg, String path) {
        this.msg = msg;
        this.path = path;
    }

    Lang(String msg) {
        this.msg = msg;
        this.path = this.name().toLowerCase().replace("_", "-");
    }

    private String getPath() {
        return path;
    }

    private String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    public String p() {
        return Util.parseColor(PREFIX.toString() + this.msg);
    }

    public String il() {
        return Util.parseColor(ILLEGAL_PREFIX.toString() + this.msg);
    }

    public String err() {
        return Util.parseColor(ERROR_PREFIX.toString().replace("{message}", this.msg));
    }

    private static FileConfiguration config;
    private static File file;
    public static void init(RegionsPlus plugin) {
        file = new File(plugin.getDataFolder(), "lang.yml");
        try {
            if (plugin.getDataFolder().mkdirs())
                plugin.getLogger().info("Created the plugin folder!");
            if (file.createNewFile())
                plugin.getLogger().info("Created lang.yml");
        } catch (IOException e) {
            plugin.getLogger().severe("Could not create lang.yml!");
            e.printStackTrace();
        }
        config = YamlConfiguration.loadConfiguration(file);
        for (Lang m : Lang.values()) {
            if (!config.isSet(m.getPath()))
                config.set(m.getPath(), m.getMsg());
            else if (!config.getString(m.getPath()).equals(m.getMsg()))
                m.setMsg(config.getString(m.getPath()));
        }
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save lang.yml");
            e.printStackTrace();
        }
    }

    public static void reloadLang() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public String toString() {
        return Util.parseColor(this.msg);
    }
}
