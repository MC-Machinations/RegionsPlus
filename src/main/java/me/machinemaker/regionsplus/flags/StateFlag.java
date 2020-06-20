package me.machinemaker.regionsplus.flags;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum StateFlag implements IFlag<StateFlag.State> { //Protection
    // Blocks
    BLOCK_PLACE(State.ALLOW, "Block placing"),
    BLOCK_BREAK(State.ALLOW, "Block breaking"),
    VEHICLE_PLACE(State.ALLOW, "Place minecarts, etc."),
    VEHICLE_DESTROY(State.ALLOW, "Destroy minecarts, etc."),

    BUILD(State.ALLOW, "Everything building related",
            StateFlag.BLOCK_PLACE,
            StateFlag.BLOCK_BREAK,
            StateFlag.VEHICLE_PLACE,
            StateFlag.VEHICLE_DESTROY
    ),

    // Interactions
    RIDE(State.ALLOW, "Ride minecarts, animals"),
    USE_INVENTORIES(State.ALLOW, "Use inventories"),
    SLEEP(State.ALLOW, "Use beds to sleep"),
    LIGHTER(State.ALLOW, "Place fire (flint & steel, fire charge, etc.)"),
    SPAWN_EGG(State.ALLOW, "Use spawn eggs"),

    TNT(State.ALLOW, "Light TNT"),
    TRAMPLING(State.ALLOW, "Trampling of turtle eggs and farmland"),

    USE(State.ALLOW, "Using blocks (e.g. Doors, levers, NOT inventories)",
            StateFlag.SLEEP,
            StateFlag.LIGHTER,
            StateFlag.SPAWN_EGG,
            StateFlag.TNT,
            StateFlag.TRAMPLING
    ),
    INTERACT(State.ALLOW, "Interactions with blocks & entities",
            StateFlag.RIDE,
            StateFlag.USE,
            StateFlag.USE_INVENTORIES,
            StateFlag.SLEEP,
            StateFlag.TNT,
            StateFlag.TRAMPLING
    ),



    // Damage
    DAMAGE_ANIMALS(State.ALLOW, "Damage to animals"),
    DAMAGE_MONSTERS(State.ALLOW, "Damage to monsters"),
    PVP(State.ALLOW, "Player vs. Player damage"),

    DAMAGE_PLAYERS(State.ALLOW, "Player damage from anything"),
    DAMAGE_ENTITIES(State.ALLOW, "Mob entities (not players) damage from anything",
            StateFlag.DAMAGE_ANIMALS,
            StateFlag.DAMAGE_MONSTERS
    ),
    DAMAGE(State.ALLOW, "Anything taking damage from anything",
            StateFlag.DAMAGE_PLAYERS,
            StateFlag.DAMAGE_ENTITIES
    ),

    // Movement
    ENTRY(State.ALLOW, "Entry into region"),
    EXIT(State.ALLOW, "Exit from region"),
    ENDERPEARL_USE(State.ALLOW, "Enderpearl use in region"),
    CHORUS_FRUIT_TELEPORT(State.ALLOW, "Chorus fruit teleportation"),

    // Mobs
    MOB_GRIEF(State.ALLOW, "Mob griefing"),
    CREEPER_GRIEF(State.ALLOW, "Creeper griefing (explosions)"),
    ENDERDRAGON_GRIEF(State.ALLOW, "Enderdragon griefing (breaking blocks)"),
    GHAST_GRIEF(State.ALLOW, "Ghast griefing (explosions)"),
    ENDERMAN_GRIEF(State.ALLOW, "Enderman griefing (picking up blocks)"),
    SNOWMAN_GRIEF(State.ALLOW, "Snowman griefing (placing snow)"),
    WITHER_GRIEF(State.ALLOW, "Wither griefing (explosions)"),

    MOB_SPAWNING(State.ALLOW, "Mob spawning"),


    // Other explosions/fire
    EXPLOSION(State.ALLOW, "Explosions"),
    FIRE_SPREAD(State.ALLOW, "Fire spread (intensive)"),
    DESTROY_PAINTINGS(State.ALLOW, "Paintings destroyed by non-players"),
    DESTROY_ITEMFRAMES(State.ALLOW, "Itemframes destroyed by non-players");


    private final State defaultState;
    final String description;
    List<StateFlag> children;

    StateFlag(State defaultState, String desc, StateFlag... children) {
        this.defaultState = defaultState;
        this.description = desc;
        this.children = Arrays.asList(children);
    }

    public State getDefault() {
        return this.defaultState;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

    public String toUpper() { return this.name().toUpperCase(); }

    public enum State {
        ALLOW,
        DENY;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }

        public String u() {
            return this.name().toUpperCase();
        }
    }

    /*TODO Flag list
     * Nether portal creation
     * Elytra flying
     */
}
