package me.machinemaker.regionsplus;

import co.aikar.commands.*;
import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.machinemaker.configmanager.BaseConfig;
import me.machinemaker.configmanager.annotations.Description;
import me.machinemaker.configmanager.annotations.NewConfig;
import me.machinemaker.configmanager.annotations.Param;
import me.machinemaker.configmanager.annotations.Path;
import me.machinemaker.configmanager.configs.ConfigFormat;
import me.machinemaker.regionsplus.commands.RegionCmd;
import me.machinemaker.regionsplus.commands.RegionPlusCmd;
import me.machinemaker.regionsplus.commands.SelectionCmd;
import me.machinemaker.regionsplus.commands.Tool;
import me.machinemaker.regionsplus.events.block.BlockBreak;
import me.machinemaker.regionsplus.events.block.BlockPlace;
import me.machinemaker.regionsplus.events.entity.EntityDamage;
import me.machinemaker.regionsplus.events.entity.EntityDamageByEntity;
import me.machinemaker.regionsplus.events.player.PlayerInteract;
import me.machinemaker.regionsplus.events.player.PlayerInteractEntity;
import me.machinemaker.regionsplus.events.player.PlayerMove;
import me.machinemaker.regionsplus.flags.Flags;
import me.machinemaker.regionsplus.flags.StateFlag;
import me.machinemaker.regionsplus.flags.StateFlag.State;
import me.machinemaker.regionsplus.misc.Binder;
import me.machinemaker.regionsplus.misc.Lang;
import me.machinemaker.regionsplus.misc.Log4JFilter;
import me.machinemaker.regionsplus.misc.Sender;
import me.machinemaker.regionsplus.regions.BaseRegion;
import me.machinemaker.regionsplus.regions.Region;
import me.machinemaker.regionsplus.regions.RegionType;
import me.machinemaker.regionsplus.regions.Selection;
import me.machinemaker.regionsplus.utils.Materials;
import me.machinemaker.regionsplus.utils.Metrics;
import me.machinemaker.regionsplus.utils.RegionManager;
import me.machinemaker.regionsplus.utils.SelectionManager;
import me.machinemaker.regionsplus.worlds.RegionWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Singleton
public final class RegionsPlus extends JavaPlugin {

    private final int PLUGIN_ID = 7517;

    private static TaskChainFactory taskChainFactory;
    public static <T> TaskChain<T> newChain() {
        return taskChainFactory.newChain();
    }
    public static <T> TaskChain<T> newSharedChain(String name) {
        return taskChainFactory.newSharedChain(name);
    }
    public static java.util.logging.Logger logger;

    private PaperCommandManager cm;

    private SelectionManager selectionManager;
    private RegionManager regionManager;

    private List<? extends BaseCommand> commandClasses;
    private List<Listener> events;
    private Injector injector;

    //TODO Make custom util function for replacing lots of placeholders e.g. {name}, {region}
    //TODO Custom logging to console + log file

    @Override
    public void onEnable() {
        new Metrics(this, PLUGIN_ID);

        MainConfig config = new MainConfig();
        config.init(this);

        taskChainFactory = BukkitTaskChainFactory.create(this);
        logger = this.getLogger();

        // Dep. Injection
        commandClasses = Arrays.asList(new RegionCmd(), new RegionPlusCmd(), new SelectionCmd(), new Tool());
        events = Arrays.asList(
                // Selection tool
                new me.machinemaker.regionsplus.tool.PlayerInteract(),
                // Blocks
                new BlockPlace(),
                new BlockBreak(),
                // Player
                new PlayerInteract(),
                new PlayerInteractEntity(),
                new PlayerMove(),
                // Entity
                new EntityDamage(),
                new EntityDamageByEntity()
        );


        selectionManager = new SelectionManager();
        regionManager = new RegionManager(this);
        Binder binder = new Binder(this, regionManager, selectionManager, config);
        this.injector = binder.createInjector();
        commandClasses.forEach(c -> this.injector.injectMembers(c));
        events.forEach(e -> this.injector.injectMembers(e));
        this.injector.injectMembers(regionManager);

        cm = new PaperCommandManager(this);

        Materials.load();

        Lang.init(this);

        cm.enableUnstableAPI("help");
        setupConditions();
        setupContexts();
        setupCompletions();
        setupCommands();

        registerEvents();

        // Logging filter
        Logger coreLogger = (Logger) LogManager.getRootLogger();
        coreLogger.addFilter(new Log4JFilter());
    }

    @Singleton
    @NewConfig(name = "config", fileName = "config.yml", format = ConfigFormat.YAML)
    public class MainConfig extends BaseConfig {

        @Param(description = "Use action bar for \"Not Allowed\" messages", path = "messages.use_action_bar")
        public Boolean useActionBar = true;

        @Param(description = "Delay between messages (in milliseconds)", path = "messages.delay")
        public Long msgDelay = 1000L;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    private void setupConditions() {
        cm.getCommandConditions().addCondition("selection", context -> {
            if (context.getIssuer().getPlayer() != null) {
                if (!selectionManager.hasSelection(context.getIssuer().getPlayer()))
                    throw new ConditionFailedException(Lang.NO_SELECTION.toString());
                Selection selection = selectionManager.getSelection(context.getIssuer().getPlayer());
                if (selection.getPos1() == null || selection.getPos2() == null)
                    throw new ConditionFailedException(Lang.PARTIAL_SELECTION.toString());
            }
        });
    }

    private void setupContexts() {
        CommandContexts<BukkitCommandExecutionContext> contexts = cm.getCommandContexts();
        contexts.registerIssuerOnlyContext(Sender.class, context -> {
            if (context.getIssuer().isPlayer()) return new Sender(context.getPlayer());
            else return new Sender(context.getSender());
        });
        contexts.registerContext(Region.class, context -> {
            RegionWorld world = regionManager.getWorld(context.getPlayer().getWorld().getName());
            String input = context.popFirstArg();
            Optional<Region> region = world.getRegion(input);
            if (region.isPresent()) return region.get();
            throw new InvalidCommandArgument(Lang.NOT_VALID_REGION.toString().replace("{world}", world.getName()), false);
        });
        contexts.registerContext(BaseRegion.class, context -> {
            String input = context.popFirstArg();
            if (input.equals("$&CURRENT&$") && context.getIssuer().isPlayer()) {
                RegionWorld rgWorld = regionManager.getWorld(context.getPlayer().getWorld().getName());
                return rgWorld.getRegions().stream().filter(rg -> rg.inRegion(context.getPlayer().getLocation())).min(Comparator.comparingLong(Region::getVolume)).map(rg -> (BaseRegion) rg).orElse(rgWorld.getGlobal());
            } else {
                Optional<? extends BaseRegion> region;
                String worldString = input.contains(":") ? input.split(":")[0] : context.getIssuer().isPlayer() ? context.getPlayer().getWorld().getName() : null;
                RegionWorld world;
                if (input.contains(":")) {
                    world = regionManager.getWorld(worldString);
                    if (isNull(world))
                        throw new InvalidCommandArgument(Lang.NOT_VALID_WORLD.toString().replace("{world}", worldString), false);
                    region = world.getRegionAll(input.split(":")[1]);
                } else if (!isNull(context.getPlayer()))
                    region = regionManager.getWorld(context.getPlayer().getWorld().getName()).getRegionAll(input);
                else throw new InvalidCommandArgument(Lang.NO_WORLD_CONSOLE.toString(), false);
                if (!region.isPresent())
                    throw new InvalidCommandArgument(Lang.NOT_VALID_REGION.toString().replace("{world}", worldString), false);
                return region.get();
            }
        });
        contexts.registerContext(OfflinePlayer[].class, context -> {
            String input = context.popFirstArg();
            Set<OfflinePlayer> players = new HashSet<>();
            Arrays.stream(input.split(",")).forEach(name -> {
                OfflinePlayer player = Bukkit.getOfflinePlayer(name);
                if (player.hasPlayedBefore() || player.getName() != null) players.add(player);
            });
            return players.toArray(new OfflinePlayer[0]);
        });
    }

    private void setupCompletions() {
        CommandCompletions<BukkitCommandCompletionContext> completions = cm.getCommandCompletions();
        completions.registerStaticCompletion("regionTypes", Arrays.stream(RegionType.values()).filter(type -> type != RegionType.GLOBAL).map(type -> type.name().toUpperCase()).collect(Collectors.toList()));
        completions.setDefaultCompletion("regionTypes", RegionType.class);

        completions.registerAsyncCompletion("regionNames", context -> {
            boolean showGlobal = Boolean.parseBoolean(context.getConfig("global", "false"));
            List<String> names = new ArrayList<>();
            if (!isNull(context.getPlayer())) {
                names = regionManager.getWorld(context.getPlayer().getWorld().getName()).getRegions().stream().map(BaseRegion::getName).collect(Collectors.toList());
                if (showGlobal) names.add(regionManager.getWorld(context.getPlayer().getWorld().getName()).getGlobal().getName());
            }
            for (RegionWorld world : regionManager.getWorlds()) {
                for (Region region : world.getRegions())
                    names.add(region.getNameKey());
                if (showGlobal) names.add(world.getName() + ":" + world.getGlobal().getName());
            }
            return names;
        });

        completions.registerAsyncCompletion("allplayers", context -> Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).collect(Collectors.toList()));
        completions.registerAsyncCompletion("allplayersarray", context -> {
            OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();
            boolean useRegion = Boolean.parseBoolean(context.getConfig("region", "false"));
            boolean isAdmin = Boolean.parseBoolean(context.getConfig("admin", "false"));
            boolean isUser = Boolean.parseBoolean(context.getConfig("user", "false"));
            if (useRegion) {
                BaseRegion region = context.getContextValue(BaseRegion.class);
                if (isAdmin)
                    offlinePlayers = region.getAdmins().stream().map(Bukkit::getOfflinePlayer).toArray(OfflinePlayer[]::new);
                else {
                    Set<OfflinePlayer> players = new HashSet<>(Arrays.asList(offlinePlayers));
                    players.removeAll(region.getAdmins().stream().map(Bukkit::getOfflinePlayer).collect(Collectors.toSet()));
                    offlinePlayers = players.toArray(new OfflinePlayer[0]);
                }
                if (isUser)
                    offlinePlayers = region.getUsers().stream().map(Bukkit::getOfflinePlayer).toArray(OfflinePlayer[]::new);
                else {
                    Set<OfflinePlayer> players = new HashSet<>(Arrays.asList(offlinePlayers));
                    players.removeAll(region.getUsers().stream().map(Bukkit::getOfflinePlayer).collect(Collectors.toSet()));
                    offlinePlayers = players.toArray(new OfflinePlayer[0]);
                }
            }
            String[] split = context.getInput().split(",");
            LinkedList<String> splitList = new LinkedList<>(Arrays.asList(split));
            String last = context.getInput().endsWith(",") ? "," : splitList.removeLast();
            return Arrays.stream(offlinePlayers)
                    .filter(player -> player.getName() != null && !splitList.contains(player.getName()) && (StringUtil.startsWithIgnoreCase(player.getName(), last) || context.getInput().endsWith(",")))
                    .map(player -> split.length > 1 || context.getInput().endsWith(",") ? String.join(",", splitList) + "," + player.getName() : player.getName())
                    .collect(Collectors.toList());
        });

        completions.registerAsyncCompletion("flaginput", context -> {
            Flags f = context.getContextValue(Flags.class);
            switch (f.getType()) {
                case STATE:
                    return Arrays.stream(StateFlag.State.values()).map(State::u).collect(Collectors.toList());
                case STRING:
                    return null;
            }
            return null;
        });
    }

    private void setupCommands() {
        this.commandClasses.forEach(c -> cm.registerCommand(c));

        try {
            cm.getLocales().loadYamlLanguageFile("lang.yml", Locale.ENGLISH);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        events.forEach(e -> pm.registerEvents(e, this));
    }
}
