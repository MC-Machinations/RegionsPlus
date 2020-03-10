package me.machinemaker.regionsplus.flags;

public enum Flags {
    /**
     * State Flags
     */
    // Blocks
    BUILD(FlagTypes.STATE),
    BLOCK_PLACE(FlagTypes.STATE),
    BLOCK_BREAK(FlagTypes.STATE),
    VEHICLE_PLACE(FlagTypes.STATE),
    VEHICLE_DESTROY(FlagTypes.STATE),

    // Interactions
    INTERACT(FlagTypes.STATE),
    RIDE(FlagTypes.STATE),
    USE(FlagTypes.STATE),
    USE_INVENTORIES(FlagTypes.STATE),
    SLEEP(FlagTypes.STATE),
    LIGHTER(FlagTypes.STATE),
    SPAWN_EGG(FlagTypes.STATE),

    TNT(FlagTypes.STATE),
    TRAMPLING(FlagTypes.STATE),

    // Damage
    DAMAGE(FlagTypes.STATE),
    DAMAGE_PLAYERS(FlagTypes.STATE),
    DAMAGE_ENTITIES(FlagTypes.STATE),
    DAMAGE_ANIMALS(FlagTypes.STATE),
    DAMAGE_MONSTERS(FlagTypes.STATE),
    PVP(FlagTypes.STATE),

    // Movement
    ENTRY(FlagTypes.STATE),
    EXIT(FlagTypes.STATE),
    ENDERPEARL_USE(FlagTypes.STATE),
    CHORUS_FRUIT_TELEPORT(FlagTypes.STATE),

    // Mobs
    MOB_GRIEF(FlagTypes.STATE),
    CREEPER_GRIEF(FlagTypes.STATE),
    ENDERDRAGON_GRIEF(FlagTypes.STATE),
    GHAST_GRIEF(FlagTypes.STATE),
    ENDERMAN_GRIEF(FlagTypes.STATE),
    SNOWMAN_GRIEF(FlagTypes.STATE),
    WITHER_GRIEF(FlagTypes.STATE),
    MOB_SPAWNING(FlagTypes.STATE),


    // Other explosions/fire
    EXPLOSION(FlagTypes.STATE),
    FIRE_SPREAD(FlagTypes.STATE),
    DESTROY_PAINTINGS(FlagTypes.STATE),
    DESTROY_ITEMFRAMES(FlagTypes.STATE),

    /**
     * String Flags
     */
    // Movement
    ENTER_CHAT(FlagTypes.STRING),
    ENTER_TITLE(FlagTypes.STRING),
    LEAVE_CHAT(FlagTypes.STRING),
    LEAVE_TITLE(FlagTypes.STRING),
    ENTER_DENY_CHAT(FlagTypes.STRING),
    EXIT_DENY_CHAT(FlagTypes.STRING);

    FlagTypes type;
    public int index;
    
    Flags(FlagTypes type) {
        this.type = type;
    }

    public FlagTypes getType() {
        return type;
    }

    public String u() {
        return this.name().toUpperCase();
    }
    
    public enum FlagTypes {
        STATE,
        STRING
    }

    static {
        int count = 0;
        for (Flags f : Flags.values()) {
            f.index = count;
            count++;
        }
    }
}
