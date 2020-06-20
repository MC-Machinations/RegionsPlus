package me.machinemaker.regionsplus.events.entity;

import me.machinemaker.regionsplus.events.RegionEvent;
import me.machinemaker.regionsplus.flags.StateFlag;
import me.machinemaker.regionsplus.flags.StateFlag.State;
import me.machinemaker.regionsplus.regions.BaseRegion;
import me.machinemaker.regionsplus.utils.Entities;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static me.machinemaker.regionsplus.flags.StateFlag.State.DENY;

public class EntityDamageByEntity extends RegionEvent<StateFlag> {

    public EntityDamageByEntity() {
        super("EntityDamageByEntityEvent", new StateFlag[]{StateFlag.DAMAGE, StateFlag.DAMAGE_PLAYERS, StateFlag.DAMAGE_ENTITIES, StateFlag.DAMAGE_ANIMALS, StateFlag.DAMAGE_MONSTERS, StateFlag.PVP});
    }

    @EventHandler(priority = EventPriority.NORMAL) // Don't ignore cancelled because you have to show messages if its a player
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();
        this.allow(
                event.isCancelled(),
                damaged.getLocation(),
                damager instanceof Player ? (Player) damager : null,
                (r, f, regions) -> check(r, f, damaged, damager),
                event::setCancelled,
                damager instanceof Player
        );
    }

    private boolean check(BaseRegion region, StateFlag flag, Entity damaged, Entity damager) {
        State value = region.getStateFlag(flag).getValue();
        boolean children = flag.getChildren().stream().map(f -> check(region, f, damaged, damager)).reduce(Boolean::logicalOr).orElse(false);
        switch (flag) {
            case DAMAGE:
                return value == DENY || children;
            case DAMAGE_PLAYERS:
                return value == DENY && damaged instanceof Player;
            case DAMAGE_ENTITIES:
                return (value == DENY && !(damaged instanceof Player)) || children;
            case DAMAGE_ANIMALS:
                return value == DENY && Entities.isNotHostile(damaged);
            case DAMAGE_MONSTERS:
                return value == DENY && Entities.isHostile(damaged);
            case PVP:
                return value == DENY && damaged instanceof Player && damager instanceof Player;
            default:
                return false;

        }
    }
}
