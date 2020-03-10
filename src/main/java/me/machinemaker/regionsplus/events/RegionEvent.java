package me.machinemaker.regionsplus.events;

import me.machinemaker.regionsplus.RegionManager;
import me.machinemaker.regionsplus.RegionsPlus;
import me.machinemaker.regionsplus.misc.Lang;
import me.machinemaker.regionsplus.regions.BaseRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

public abstract class RegionEvent<T> implements Listener {

    protected final List<T> flags;

    @SafeVarargs
    protected RegionEvent(String event, T... flags) {
        if (flags.length == 0)
            RegionsPlus.logger.warning(event + " does not have any flags registered to it!");
        this.flags = Arrays.asList(flags);
    }

    /**
     * Processes whether or not to allow an action
     * @param initialState Initial state, passed from previous events or states
     * @param loc Location of the event. Used for determining which regions to check
     * @param p Player associated with the event
     * @param test Check if to allow/deny the event return TRUE for deny
     * @param denyAction Deny action
     * @param showAlert should send message to player or not
     * @return allow
     */
    protected boolean allow(boolean initialState, Location loc, Player p, BiFunction<BaseRegion, T, Boolean> test, @Nullable Consumer<Boolean> denyAction, boolean showAlert) {
        AtomicBoolean deny = new AtomicBoolean(initialState);
        RegionManager.get().getRegions(loc).stream()
                .filter(r -> !r.hasAdmin(p.getUniqueId()))
                .forEachOrdered(r -> deny.set(flags.stream().map(f -> test.apply(r, f)).reduce(false, Boolean::logicalOr)));
        if (deny.get() && showAlert) RegionManager.get().sendMessage(p, Lang.ILLEGAL_ACTION.toString()); //TODO only send 1 message every few seconds, instead of every time
        if (nonNull(denyAction)) denyAction.accept(deny.get());
        return deny.get();
    }

    /**
     * Processes whether or not to allow an action
     * @param initialState Initial state, passed from previous events or states
     * @param loc Location of the event. Used for determining which regions to check
     * @param p Player associated with the event
     * @param test Check if to allow/deny the event
     * @param denyAction Deny action
     * @return allow
     */
    protected boolean allow(boolean initialState, Location loc, Player p, BiFunction<BaseRegion, T, Boolean> test, @Nullable Consumer<Boolean> denyAction) {
        return allow(initialState, loc, p, test, denyAction, true);
    }
}
