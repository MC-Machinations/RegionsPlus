package me.machinemaker.regionsplus.events;

import com.google.inject.Inject;
import me.machinemaker.regionsplus.RegionsPlus;
import me.machinemaker.regionsplus.misc.Lang;
import me.machinemaker.regionsplus.misc.TriFunction;
import me.machinemaker.regionsplus.regions.BaseRegion;
import me.machinemaker.regionsplus.utils.RegionManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public abstract class RegionEvent<F extends Enum<F>> implements Listener {
    
    @Inject
    protected RegionManager regionManager;

    protected final List<F> flags;

    protected RegionEvent(String event, @NotNull F[] flags) {
        if (flags.length == 0)
            RegionsPlus.logger.warning(event + " does not have any flags registered to it!");
        this.flags = Arrays.asList(flags);
    }

    protected <B extends Enum<B>> boolean processFlags(boolean initialState, Location loc, Player p, TriFunction<BaseRegion, B, List<BaseRegion>, Boolean> test, @NotNull BiConsumer<Boolean, BaseRegion> denyAction, boolean showAlert, List<B> flags) {
        AtomicBoolean deny = new AtomicBoolean(initialState);
        List<BaseRegion> regions = regionManager.getRegions(loc, p);
        final BaseRegion[] region = new BaseRegion[1];
        regions.forEach(r -> deny.set(flags.stream().map(f -> {
            if (test.apply(r, f, regions)) {
                if (isNull(region[0])) region[0] = r;
                return true;
            } else return false;

        }).reduce(false, Boolean::logicalOr)));
        if (deny.get() && showAlert && nonNull(p)) regionManager.sendMessage(p, Lang.ILLEGAL_ACTION.il());
        denyAction.accept(deny.get(), region[0]);
        return deny.get();
    }

    protected <B extends Enum<B>> boolean processFlags(boolean initialState, Location loc, Player p, TriFunction<BaseRegion, B, List<BaseRegion>, Boolean> test, @Nullable Consumer<Boolean> denyAction, boolean showAlert, List<B> flags) {
        AtomicBoolean deny = new AtomicBoolean(initialState);
        List<BaseRegion> regions = regionManager.getRegions(loc, p);
        regions.forEach(r -> deny.set(flags.stream().map(f -> test.apply(r, f, regions)).reduce(false, Boolean::logicalOr)));
        if (deny.get() && showAlert && nonNull(p)) regionManager.sendMessage(p, Lang.ILLEGAL_ACTION.il());
        if (nonNull(denyAction)) denyAction.accept(deny.get());
        return deny.get();
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
    protected boolean allow(boolean initialState, Location loc, Player p, TriFunction<BaseRegion, F, List<BaseRegion>, Boolean> test, @Nullable Consumer<Boolean> denyAction, boolean showAlert) {
        return processFlags(initialState, loc, p, test, denyAction, showAlert, this.flags);
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
    protected boolean allow(boolean initialState, Location loc, Player p, TriFunction<BaseRegion, F, List<BaseRegion>, Boolean> test, @Nullable Consumer<Boolean> denyAction) {
        return allow(initialState, loc, p, test, denyAction, true);
    }

    /**
     * Processes whether or not to allow an action (without Player)
     * @param initialState Initial state, passed from previous events or states
     * @param loc Location of the event. Used for determining which regions to check
     * @param test Check if to allow/deny the event
     * @param denyAction Deny action
     * @return allow
     */
    protected boolean allow(boolean initialState, Location loc, TriFunction<BaseRegion, F, List<BaseRegion>, Boolean> test, @Nullable Consumer<Boolean> denyAction) {
        return allow(initialState, loc, null, test, denyAction);
    }

    /**
     * Processes whether or not to allow an action (with a denyAction that is region-dependant)
     * @param initialState Initial state, passed from previous events or states
     * @param loc Location of the event. Used for determining which regions to check
     * @param p Player associated with the event
     * @param test Check if to allow/deny the event
     * @param denyAction Deny action (with region)
     * @return allow
     */
    protected boolean allow(boolean initialState, Location loc, Player p, TriFunction<BaseRegion, F, List<BaseRegion>, Boolean> test, @NotNull BiConsumer<Boolean, BaseRegion> denyAction, boolean showAlert) {
        return processFlags(initialState, loc, p, test, denyAction, showAlert, this.flags);
    }
}
