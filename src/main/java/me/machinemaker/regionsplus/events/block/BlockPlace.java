package me.machinemaker.regionsplus.events.block;

import me.machinemaker.regionsplus.events.RegionEvent;
import me.machinemaker.regionsplus.flags.StateFlag;
import me.machinemaker.regionsplus.flags.StateFlag.State;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace extends RegionEvent<StateFlag> {

    public BlockPlace() {
        super("BlockPlaceEvent", new StateFlag[]{StateFlag.BLOCK_PLACE, StateFlag.BUILD});
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        this.allow(
                event.canBuild(),
                event.getBlock().getLocation(),
                event.getPlayer(),
                (r, f, regions) -> r.getStateFlag(f).getValue() == State.DENY,
                deny -> event.setBuild(!deny),
                event.canBuild()
        );
    }
}
