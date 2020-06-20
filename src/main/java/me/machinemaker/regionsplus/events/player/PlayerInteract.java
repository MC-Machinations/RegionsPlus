package me.machinemaker.regionsplus.events.player;

import me.machinemaker.regionsplus.events.RegionEvent;
import me.machinemaker.regionsplus.flags.StateFlag;
import me.machinemaker.regionsplus.flags.StateFlag.State;
import me.machinemaker.regionsplus.regions.BaseRegion;
import me.machinemaker.regionsplus.utils.Materials;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static java.util.Objects.isNull;
import static me.machinemaker.regionsplus.flags.StateFlag.State.DENY;

public class PlayerInteract extends RegionEvent<StateFlag> {

    public PlayerInteract() {
        super("PlayerInteractEvent", new StateFlag[]{StateFlag.INTERACT, StateFlag.USE, StateFlag.USE_INVENTORIES, StateFlag.SLEEP, StateFlag.LIGHTER, StateFlag.SPAWN_EGG, StateFlag.TRAMPLING, StateFlag.TNT});
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.useInteractedBlock() == Result.DENY) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR || isNull(event.getClickedBlock())) return;
        Block clickedBlock = event.getClickedBlock();
        Material heldItem = event.getHand() == EquipmentSlot.HAND ? event.getPlayer().getInventory().getItemInMainHand().getType() : event.getPlayer().getInventory().getItemInOffHand().getType();
        this.allow(
                true,
                clickedBlock.getLocation(),
                event.getPlayer(),
                (r, f, regions) -> check(r, f, clickedBlock, heldItem, event.getAction(), event.getPlayer().isSneaking()),
                deny -> {
                    if (deny) event.setUseInteractedBlock(Result.DENY);
                    else event.setUseInteractedBlock(Result.ALLOW);
                }
        );
    }

    private boolean check(BaseRegion r, StateFlag flag, Block clickedBlock, Material heldItem, Action action, boolean isSneaking) {
        State value = r.getStateFlag(flag).getValue();
        boolean isModification = Materials.isModOnClick(clickedBlock.getType(), action);
        boolean children = flag.getChildren().stream().map(f -> check(r, f, clickedBlock, heldItem, action, isSneaking)).reduce(Boolean::logicalOr).orElse(false);
        switch (flag) {
            case INTERACT:
                return (value == DENY && isModification) || children;
            case USE:
                return (value == DENY && !Materials.isInventory(clickedBlock.getType()) && isModification) || children;
            case USE_INVENTORIES:
                return value == DENY && Materials.isInventory(clickedBlock.getType()) && isModification;
            case SLEEP:
                return value == DENY && Tag.BEDS.isTagged(clickedBlock.getType()) && isModification;
            case LIGHTER:
                return value == DENY && Materials.isLighter(heldItem) && (!isModification || isSneaking);
            case SPAWN_EGG:
                return value == DENY && Materials.isSpawnEgg(heldItem) && (!isModification || isSneaking);
            case TRAMPLING:
                return value == DENY && action == Action.PHYSICAL && clickedBlock.getType() == Material.FARMLAND;
            case TNT:
                return value == DENY && Materials.isLighter(heldItem) && clickedBlock.getType() == Material.TNT;
            default:
                return false;
        }
    }
}
