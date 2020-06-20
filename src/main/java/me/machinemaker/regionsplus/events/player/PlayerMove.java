package me.machinemaker.regionsplus.events.player;


import com.google.inject.Inject;
import me.machinemaker.regionsplus.events.BiRegionEvent;
import me.machinemaker.regionsplus.flags.StateFlag;
import me.machinemaker.regionsplus.flags.StateFlag.State;
import me.machinemaker.regionsplus.flags.StringFlag;
import me.machinemaker.regionsplus.misc.Lang;
import me.machinemaker.regionsplus.regions.GlobalRegion;
import me.machinemaker.regionsplus.utils.RegionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import static java.util.Objects.nonNull;
import static me.machinemaker.regionsplus.flags.StateFlag.State.DENY;

// TODO: Option to disable event
public class PlayerMove extends BiRegionEvent<StateFlag, StringFlag> {

    @Inject
    protected RegionManager regionManager;

    public PlayerMove() {
        super("PlayerMoveEvent", new StateFlag[]{StateFlag.ENTRY, StateFlag.EXIT}, new StringFlag[] {});
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        this.allow(
                true,
                event.getFrom(),
                player,
                (r, f, regions) -> {
                    if (r instanceof GlobalRegion) return false; // Ignore global regions
                    if (f != StateFlag.EXIT) return false;
                    return r.getStateFlag(f).getValue() == DENY && !r.inRegion(event.getTo());
                },
                (deny, r) -> {
                    if (deny) {
                        event.setTo(event.getFrom());
                        if (nonNull(r.getStringFlag(StringFlag.EXIT_DENY_CHAT)) && r.getStringFlag(StringFlag.EXIT_DENY_CHAT).isSet()) {
                            regionManager.sendMessage(player, r.getStringFlag(StringFlag.EXIT_DENY_CHAT).getValue());
                        } else regionManager.sendMessage(player, Lang.ILLEGAL_ACTION.il());
                    }
                },
                false
        );
        this.allow(
                true,
                event.getTo(),
                player,
                (r, f, regions) -> {
                    if (r instanceof GlobalRegion) return false;
                    if (f != StateFlag.ENTRY) return false;
                    State value = r.getStateFlag(f).getValue();
                    return r.getStateFlag(f).getValue() == DENY && !r.inRegion(event.getFrom());
                },
                (deny, r) -> {
                    if (deny) {
                        event.setTo(event.getFrom());
                        if (nonNull(r.getStringFlag(StringFlag.ENTER_DENY_CHAT)) && r.getStringFlag(StringFlag.ENTER_DENY_CHAT).isSet()) {
                            regionManager.sendMessage(player, r.getStringFlag(StringFlag.ENTER_DENY_CHAT).getValue());
                        } else regionManager.sendMessage(player, Lang.ILLEGAL_ACTION.il());
                    }
                },
                false
        );
    }

}
