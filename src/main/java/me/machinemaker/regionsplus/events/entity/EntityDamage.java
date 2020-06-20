package me.machinemaker.regionsplus.events.entity;

import me.machinemaker.regionsplus.events.RegionEvent;
import me.machinemaker.regionsplus.flags.StateFlag;
import me.machinemaker.regionsplus.flags.StateFlag.State;
import me.machinemaker.regionsplus.regions.BaseRegion;
import me.machinemaker.regionsplus.utils.Entities;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import static me.machinemaker.regionsplus.flags.StateFlag.State.DENY;

public class EntityDamage extends RegionEvent<StateFlag> {

    public EntityDamage() {
        super("EntityDamageEvent", new StateFlag[]{StateFlag.DAMAGE, StateFlag.DAMAGE_PLAYERS, StateFlag.DAMAGE_ENTITIES, StateFlag.DAMAGE_ANIMALS, StateFlag.DAMAGE_MONSTERS});
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        EntityDamageEvent.DamageCause cause = event.getCause();
        Entity entity = event.getEntity();
        EntityType type = event.getEntityType();
        this.allow(
                true,
                entity.getLocation(),
                (r, f, regions) -> check(r, f, entity, type),
                event::setCancelled
        );
    }

    private boolean check(BaseRegion region, StateFlag flag, Entity entity, EntityType type) {
        State value = region.getStateFlag(flag).getValue();
        boolean children = flag.getChildren().stream().map(f -> check(region, f, entity, type)).reduce(Boolean::logicalOr).orElse(true);
        switch (flag) {
            case DAMAGE:
                return value == DENY || children;
            case DAMAGE_PLAYERS:
                return value == DENY && type == EntityType.PLAYER;
            case DAMAGE_ENTITIES:
                return (value == DENY && type != EntityType.PLAYER) || children;
            case DAMAGE_ANIMALS:
                return value == DENY && Entities.isNotHostile(entity);
            case DAMAGE_MONSTERS:
                return value == DENY && Entities.isHostile(entity);
            default:
                return false;
        }
    }


}
