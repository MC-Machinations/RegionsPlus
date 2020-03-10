package me.machinemaker.regionsplus.regions;

import me.machinemaker.regionsplus.RegionsPlus;
import me.machinemaker.regionsplus.flags.Flags;
import me.machinemaker.regionsplus.flags.RegionFlag;
import me.machinemaker.regionsplus.flags.StateFlag;
import me.machinemaker.regionsplus.flags.StateFlag.State;
import me.machinemaker.regionsplus.flags.StringFlag;
import me.machinemaker.regionsplus.worlds.RegionWorld;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static net.md_5.bungee.api.ChatColor.*;
import static org.apache.commons.lang.StringUtils.repeat;

public abstract class BaseRegion {

    String taskName;

    FileConfiguration config;
    private HashMap<String, RegionFlag<StateFlag, State>> stateFlags;
    private HashMap<String, RegionFlag<StringFlag, String>> stringFlags;


    protected String name;
    protected String nameKey;
    protected RegionType type;
    protected File file;
    protected RegionWorld world;
    protected List<UUID> admins;
    protected List<UUID> users;

    private BaseComponent[] title;
    private BaseComponent[] bottom;
    private BaseComponent[] info;

    BaseRegion(RegionWorld world, String name, RegionType type) {
        this.name = name;
        this.world = world;
        this.nameKey = world.getName() + ":" + name;
        title = new ComponentBuilder(repeat("=", 15)).color(GRAY).bold(true).append(" "+name+" ").color(AQUA).bold(false).append(repeat("=", 15)).color(GRAY).bold(true).create();
        bottom = new ComponentBuilder(repeat("=", 15)).color(DARK_GRAY).bold(true).append(" "+name+" ").color(AQUA).bold(false).append(repeat("=", 15)).color(DARK_GRAY).bold(true).create();
        this.info = new ComponentBuilder("Info").color(BLUE).append("❱ ").color(GRAY).bold(true).append("(").color(YELLOW).bold(false)
                .append("world").color(GRAY).append("=").color(YELLOW).bold(true).append(world.getName()).color(DARK_AQUA).bold(false)
                    .event(new ClickEvent(Action.RUN_COMMAND, "/sel spawn " + world.getName()))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Teleport to spawn").color(AQUA).create()))
                .append(", ").color(YELLOW).event((ClickEvent) null).event((HoverEvent) null).append("type").color(GRAY).append("=").color(YELLOW).bold(true).append(type.name()).color(DARK_AQUA).bold(false)
                .append(") ").color(YELLOW).append("[List Flags]").color(GREEN)
                    .event(new ClickEvent(Action.RUN_COMMAND, "/rg flag list " + this.nameKey))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to list flags").color(GRAY).create()))
                .create();
        file = new File(world.getWorldFolder(), name + ".yml");
        try {
            if (file.createNewFile())
                RegionsPlus.logger.info("Created " + name + ".yml!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        stateFlags = new HashMap<>();
        stringFlags = new HashMap<>();
        config = YamlConfiguration.loadConfiguration(file);
        config.set("type", type.name());
        admins = config.getStringList("admins").stream().map(UUID::fromString).collect(Collectors.toList());
        users = config.getStringList("users").stream().map(UUID::fromString).collect(Collectors.toList());

        Map<String, State> stateMap = new HashMap<>();
        Map<String, String> stringMap = new HashMap<>();
        Arrays.stream(StateFlag.values()).forEach(f -> stateMap.put(f.toString(), State.valueOf(f.getDefaultValue().toString().toUpperCase())));
        Arrays.stream(StringFlag.values()).forEach(f -> stringMap.put(f.toString(), f.getDefaultValue()));
        initFlags(stateFlags, StateFlag.class, "flags.state.", stateMap, key -> State.valueOf(config.getString(key).toUpperCase()));
        initFlags(stringFlags, StringFlag.class, "flags.string.", stringMap, key -> config.getString(key));
        taskName = world.getName() + "_" + name;
        RegionsPlus.newSharedChain(taskName).async(this::saveChanges).execute();
    }

    private <T extends Enum, J> void initFlags(HashMap<String, RegionFlag<T, J>> flags, Class<T> keyClass, String path, Map<String, J> keyValues, Function<String, J> configGet) {
        keyValues.forEach((key, value) -> {
            if (config.isSet(path + key))
                flags.put(key.toUpperCase(), new RegionFlag<>((T) Enum.valueOf(keyClass, key.toUpperCase()), configGet.apply(path + key)));
            else {
                T newFlag = (T) Enum.valueOf(keyClass, key.toUpperCase());
                config.set(path + key, value);
                flags.put(key.toUpperCase(), new RegionFlag<>(newFlag, value));
            }
        });
    }

    public abstract boolean inRegion(Location loc);

    public abstract long getVolume();

    public String getName() {
        return name;
    }

    public String getNameKey() {
        return nameKey;
    }

    public List<UUID> getAdmins() {
        return admins;
    }

    public void addAdmins(Collection<UUID> playerUUIDs) {
        admins.addAll(playerUUIDs);
        saveChanges();
    }

    public boolean hasAdmin(UUID playerUUID) {
        return admins.contains(playerUUID);
    }

    public void removeAdmins(Collection<UUID> playerUUIDs) {
        admins.removeAll(playerUUIDs);
        saveChanges();
    }

    public List<UUID> getUsers() {
        return users;
    }

    public void addUsers(Collection<UUID> playerUUIDs) {
        users.addAll(playerUUIDs);
        saveChanges();
    }

    public boolean hasUser(UUID playerUUID) {
        return users.contains(playerUUID);
    }

    public void removeUsers(Collection<UUID> playerUUIDs) {
        users.removeAll(playerUUIDs);
        saveChanges();
    }

    public RegionFlag<StateFlag, State> getStateFlag(StateFlag flag) {
        return stateFlags.getOrDefault(flag.toUpper(), null);
    }

    public void setStateFlag(StateFlag flag, State state) {
        RegionFlag<StateFlag, State> rgFlag = stateFlags.getOrDefault(flag.toUpper(), null);
        if (isNull(rgFlag)) stateFlags.put(flag.toUpper(), new RegionFlag<>(flag, state));
        else rgFlag.setValue(state);
        saveChanges();
    }

    public RegionFlag<StringFlag, String> getStringFlag(StringFlag flag) {
        return stringFlags.getOrDefault(flag.toUpper(), null);
    }

    public void setStringFlag(StringFlag flag, String state) {
        RegionFlag<StringFlag, String> rgFlag = stringFlags.getOrDefault(flag.toUpper(), null);
        if (isNull(rgFlag)) stringFlags.put(flag.toUpper(), new RegionFlag<>(flag, state));
        else rgFlag.setValue(state);
        saveChanges();
    }

    public List<BaseComponent[]> getRegionInfo() {
        return Arrays.asList(
                title,
                info,
                formatPlayerList(admins, "admin"),
                formatPlayerList(users, "user"),
                bottom
        );
    }

    public List<BaseComponent[]> formatFlags(int page) {
        ComponentBuilder builder = new ComponentBuilder();
        List<Flags> flagList = Arrays.asList(Flags.values());
        flagList.stream().skip(Math.max((page - 1) * 18, 0)).limit(18).forEach(f -> {
            switch (f.getType()) {
                case STATE: {
                    RegionFlag<StateFlag, State> flag = Optional.ofNullable(stateFlags.getOrDefault(f.u(), null)).orElseThrow(IllegalStateException::new);
                    builder.append("[T]").color(flag.getValue() == State.ALLOW ? GREEN : RED).bold(true)
                            .event(new ClickEvent(Action.RUN_COMMAND, "/rg flag set " + this.nameKey + " " + flag.getType().toUpper() + " " + (flag.getValue() == State.ALLOW ? "DENY" : "ALLOW")))
                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(flag.getType().toUpper()).color(AQUA).append(" ❱ ").color(GRAY).append(flag.getValue() == State.ALLOW ? "DENY" : "ALLOW").color(flag.getValue() == State.DENY ? GREEN : RED).bold(true).create()))
                            .append(" " + flag.getType().toUpper()).color(AQUA).bold(false).event((ClickEvent) null).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(flag.getType().getDescription()).color(GRAY).create()));
                    break;
                }
                case STRING: {
                    RegionFlag<StringFlag, String> flag = Optional.ofNullable(stringFlags.getOrDefault(f.u(), null)).orElseThrow(IllegalStateException::new);
                    builder.append("[✎]").color(flag.getValue().isEmpty() ? RED : GREEN).bold(true)
                            .event(new ClickEvent(Action.SUGGEST_COMMAND, "/rg flag set " + this.nameKey + " " + flag.getType().toUpper() + " "))
                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to edit\n").color(GREEN).append("Current value: ").color(GRAY).append(flag.getValue().isEmpty() ? "(none)" : flag.getValue()).color(flag.getValue().isEmpty() ? RED : WHITE).italic(flag.getValue().isEmpty()).create()))
                            .append(" " + flag.getType().toUpper()).color(AQUA).bold(false).event((ClickEvent) null).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(flag.getType().getDescription()).color(GRAY).create()));
                    break;
                }
            }
            builder.append(!flagList.get(Math.min((page - 1) * 18 + 18 - 1, flagList.size() - 1)).u().equals(f.u()) ? "\n" : "");
        });

        builder.append((page - 1) * 18 + 18 > Flags.values().length ? repeat("\n ", (page - 1) * 18 + 18 - Flags.values().length) : "");
        return Arrays.asList(
                pageNumHeader(page, true),
                builder.create(),
                pageNumHeader(page, false)
        );
    }

    private BaseComponent[] pageNumHeader(int page, boolean isTop) {
        int maxPage = (int)Math.ceil(Flags.values().length/18.0);
        int nextPage = page + 1 > maxPage ? 1 : page + 1;
        int prevPage = page - 1 < 1 ? maxPage : page - 1;
        return new ComponentBuilder("❮❮❮").color(GREEN)
                    .event(new ClickEvent(Action.RUN_COMMAND, "/rg flag list " + this.nameKey + " " + prevPage))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("❮❮ ").color(GREEN).append("Go to page ").color(GRAY).append(prevPage+"").color(YELLOW).create()))
                .append(repeat("=", 10)).color(isTop ? GRAY : DARK_GRAY).event((ClickEvent) null).event((HoverEvent) null)
                .append(" "+this.name+" ").color(AQUA)
                .append(page+"").color(YELLOW)
                .append("/").color(GRAY)
                .append(maxPage+" ").color(GOLD)
                .append(repeat("=", 10)).color(isTop ? GRAY : DARK_GRAY)
                .append("❱❱❱").color(GREEN)
                    .event(new ClickEvent(Action.RUN_COMMAND, "/rg flag list " + this.nameKey + " " + nextPage))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("❱❱ ").color(GREEN).append("Go to page ").color(GRAY).append(nextPage+"").color(YELLOW).create()))
                .append("").event((ClickEvent) null).event((HoverEvent) null).create();
    }

    private BaseComponent[] formatPlayerList(List<UUID> uuids, String type) {
        ComponentBuilder builder = new ComponentBuilder(StringUtils.capitalize(type) + "s").color(BLUE)
                .append("❱ ").color(GRAY).bold(true)
                .append("+").color(GREEN).bold(false)
                    .event(new ClickEvent(Action.SUGGEST_COMMAND, "/rg "+type+" add " + this.nameKey + " "))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Add " + type).color(GREEN).italic(true).create()))
                .append(" ").event((ClickEvent) null).event((HoverEvent) null);
        for (int i = 0; i < uuids.size(); i++) {
            String playerName = Bukkit.getOfflinePlayer(uuids.get(i)).getName();
            builder.append(playerName).color(AQUA)
                    .append("❌").color(RED)
                        .event(new ClickEvent(Action.RUN_COMMAND, "/rg " + type + " del " + this.nameKey + " " + playerName))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Remove " + playerName).color(RED).italic(true).create()));
            if (i < uuids.size() - 1) builder.append(", ").color(YELLOW).event((ClickEvent) null).event((HoverEvent) null);
        }
        return builder.create();
    }



    private synchronized void saveChanges() {
        RegionsPlus.newSharedChain(taskName)
                .async(() -> {
                    config.set("admins", admins.stream().map(UUID::toString).collect(Collectors.toList()));
                    config.set("users", users.stream().map(UUID::toString).collect(Collectors.toList()));
                    stateFlags.forEach((key, flag) -> config.set("flags.state." + key.toLowerCase(), flag.getValue().toString()));
                    stringFlags.forEach((key, flag) -> config.set("flags.string." + key.toLowerCase(), flag.getValue()));
                })
                .async(this::saveConfig).execute();
    }

    synchronized void saveConfig() {
        try {
            this.config.save(this.file);
        } catch (IOException e) { //TODO: Logging
            e.printStackTrace();
        }
    }

    public boolean delete() {
        return file.delete(); //TODO: Logging
    }
}

