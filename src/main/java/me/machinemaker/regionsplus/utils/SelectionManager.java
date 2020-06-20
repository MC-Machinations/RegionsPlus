package me.machinemaker.regionsplus.utils;

import com.google.inject.Singleton;
import me.machinemaker.regionsplus.regions.Selection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class SelectionManager {

    private Map<UUID, Selection> selectionMap;

    public SelectionManager() {
        selectionMap = new HashMap<>();
    }

    public boolean hasSelection(Player p) {
        return selectionMap.containsKey(p.getUniqueId());
    }

    public Selection getSelection(Player p) {
        if (selectionMap.containsKey(p.getUniqueId()))
            return selectionMap.get(p.getUniqueId());
        else {
            Selection selection = new Selection();
            selectionMap.put(p.getUniqueId(), selection);
            return selection;
        }
    }
}
