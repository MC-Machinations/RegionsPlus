package me.machinemaker.regionsplus.events;

import me.machinemaker.regionsplus.RegionsPlus;
import me.machinemaker.regionsplus.misc.TriFunction;
import me.machinemaker.regionsplus.regions.BaseRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class BiRegionEvent<F extends Enum<F>, T extends Enum<T>> extends RegionEvent<F> {

    List<T> flags2;

    protected BiRegionEvent(String event, F[] flags1, @NotNull T[] flags2) {
        super(event, flags1);
        if (flags2.length == 0)
            RegionsPlus.logger.warning(event + " does not have any flags registered to it!");
        this.flags2 = Arrays.asList(flags2);
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
    protected boolean allow2(boolean initialState, Location loc, Player p, TriFunction<BaseRegion, T, List<BaseRegion>, Boolean> test, @Nullable Consumer<Boolean> denyAction, boolean showAlert) {
        return this.processFlags(initialState, loc, p, test, denyAction, showAlert, this.flags2);
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
    protected boolean allow2(boolean initialState, Location loc, Player p, TriFunction<BaseRegion, T, List<BaseRegion>, Boolean> test, @Nullable Consumer<Boolean> denyAction) {
        return allow2(initialState, loc, p, test, denyAction, true);
    }

    /**
     * Processes whether or not to allow an action (without Player)
     * @param initialState Initial state, passed from previous events or states
     * @param loc Location of the event. Used for determining which regions to check
     * @param test Check if to allow/deny the event
     * @param denyAction Deny action
     * @return allow
     */
    protected boolean allow2(boolean initialState, Location loc, TriFunction<BaseRegion, T, List<BaseRegion>, Boolean> test, @Nullable Consumer<Boolean> denyAction) {
        return allow2(initialState, loc, null, test, denyAction);
    }
}
