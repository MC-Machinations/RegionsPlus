package me.machinemaker.regionsplus;

import me.machinemaker.regionsplus.regions.Selection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectionManager {

    private static SelectionManager instance = new SelectionManager();
    private SelectionManager() { }
    public static SelectionManager get() {
        return instance;
    }

    private Map<UUID, Selection> selectionMap;
    public void init() {
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
