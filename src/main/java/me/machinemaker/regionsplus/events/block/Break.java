package me.machinemaker.regionsplus.events.block;

import me.machinemaker.regionsplus.events.RegionEvent;
import me.machinemaker.regionsplus.flags.StateFlag;
import me.machinemaker.regionsplus.flags.StateFlag.State;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class Break extends RegionEvent<StateFlag> {


    public Break() {
        super("BlockBreakEvent", StateFlag.BLOCK_BREAK, StateFlag.BUILD);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        this.allow(
            true,
            event.getBlock().getLocation(),
            event.getPlayer(),
            (r, f) -> r.getStateFlag(f).getValue() == State.DENY,
            event::setCancelled
        );
    }
}
