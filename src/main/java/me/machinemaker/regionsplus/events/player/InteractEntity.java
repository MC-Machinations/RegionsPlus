package me.machinemaker.regionsplus.events.player;

import me.machinemaker.regionsplus.events.RegionEvent;
import me.machinemaker.regionsplus.flags.StateFlag;
import me.machinemaker.regionsplus.flags.StateFlag.State;
import me.machinemaker.regionsplus.utils.Entities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class InteractEntity extends RegionEvent<StateFlag> {

    public InteractEntity() {
        super("PlayerInteractEntityEvent", StateFlag.RIDE, StateFlag.INTERACT, StateFlag.USE);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        this.allow(
                true,
                event.getRightClicked().getLocation(),
                event.getPlayer(),
                (r, f) -> {
                    State value = r.getStateFlag(f).getValue();
                    switch (f) {
                        case RIDE:
                        case INTERACT:
                        case USE:
                            return value == State.DENY && Entities.isRideable(event.getRightClicked());
                        default:
                            return false;
                    }
                },
                event::setCancelled
        );
    }
}
