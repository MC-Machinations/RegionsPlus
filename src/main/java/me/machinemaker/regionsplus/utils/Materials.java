package me.machinemaker.regionsplus.utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;

import java.util.*;
import java.util.stream.Stream;

public class Materials {

    private static final int ACTION_ON_LEFT     = 0b0001;
    private static final int ACTION_ON_RIGHT    = 0b0010;
    private static final int ACTION_ON_PHYSICAL = 0b0100;
    private static final int EXECUTES_ACTION    = 0b1000;

    private static final BiMap<EntityType, Material> ENTITY_MAP = HashBiMap.create();
    private static final Map<Material, Integer> MATERIALS = new EnumMap<>(Material.class);

    private static final Set<Material> SHULKER_BOXES = new HashSet<>();

    static {

        SHULKER_BOXES.add(Material.SHULKER_BOX);
        SHULKER_BOXES.add(Material.WHITE_SHULKER_BOX);
        SHULKER_BOXES.add(Material.ORANGE_SHULKER_BOX);
        SHULKER_BOXES.add(Material.MAGENTA_SHULKER_BOX);
        SHULKER_BOXES.add(Material.LIGHT_BLUE_SHULKER_BOX);
        SHULKER_BOXES.add(Material.YELLOW_SHULKER_BOX);
        SHULKER_BOXES.add(Material.LIME_SHULKER_BOX);
        SHULKER_BOXES.add(Material.PINK_SHULKER_BOX);
        SHULKER_BOXES.add(Material.GRAY_SHULKER_BOX);
        SHULKER_BOXES.add(Material.LIGHT_GRAY_SHULKER_BOX);
        SHULKER_BOXES.add(Material.CYAN_SHULKER_BOX);
        SHULKER_BOXES.add(Material.PURPLE_SHULKER_BOX);
        SHULKER_BOXES.add(Material.BLUE_SHULKER_BOX);
        SHULKER_BOXES.add(Material.BROWN_SHULKER_BOX);
        SHULKER_BOXES.add(Material.GREEN_SHULKER_BOX);
        SHULKER_BOXES.add(Material.RED_SHULKER_BOX);
        SHULKER_BOXES.add(Material.BLACK_SHULKER_BOX);

        ENTITY_MAP.put(EntityType.PAINTING, Material.PAINTING);
        ENTITY_MAP.put(EntityType.ARROW, Material.ARROW);
        ENTITY_MAP.put(EntityType.SNOWBALL, Material.SNOWBALL);
        ENTITY_MAP.put(EntityType.FIREBALL, Material.FIRE_CHARGE);
        ENTITY_MAP.put(EntityType.ENDER_PEARL, Material.ENDER_PEARL);
        ENTITY_MAP.put(EntityType.THROWN_EXP_BOTTLE, Material.EXPERIENCE_BOTTLE);
        ENTITY_MAP.put(EntityType.ITEM_FRAME, Material.ITEM_FRAME);
        ENTITY_MAP.put(EntityType.PRIMED_TNT, Material.TNT);
        ENTITY_MAP.put(EntityType.FIREWORK, Material.FIREWORK_ROCKET);
        ENTITY_MAP.put(EntityType.MINECART_COMMAND, Material.COMMAND_BLOCK_MINECART);
        ENTITY_MAP.put(EntityType.BOAT, Material.OAK_BOAT);
        ENTITY_MAP.put(EntityType.MINECART, Material.MINECART);
        ENTITY_MAP.put(EntityType.MINECART_CHEST, Material.CHEST_MINECART);
        ENTITY_MAP.put(EntityType.MINECART_FURNACE, Material.FURNACE_MINECART);
        ENTITY_MAP.put(EntityType.MINECART_TNT, Material.TNT_MINECART);
        ENTITY_MAP.put(EntityType.MINECART_HOPPER, Material.HOPPER_MINECART);
        ENTITY_MAP.put(EntityType.SPLASH_POTION, Material.POTION);
        ENTITY_MAP.put(EntityType.EGG, Material.EGG);
        ENTITY_MAP.put(EntityType.ARMOR_STAND, Material.ARMOR_STAND);
        ENTITY_MAP.put(EntityType.ENDER_CRYSTAL, Material.END_CRYSTAL);
        
        MATERIALS.put(Material.AIR, 0);
        MATERIALS.put(Material.STONE, 0);
        MATERIALS.put(Material.GRASS_BLOCK, 0);
        MATERIALS.put(Material.DIRT, 0);
        MATERIALS.put(Material.COBBLESTONE, 0);
        MATERIALS.put(Material.BEDROCK, 0);
        MATERIALS.put(Material.WATER, 0);
        MATERIALS.put(Material.LAVA, 0);
        MATERIALS.put(Material.SAND, 0);
        MATERIALS.put(Material.GRAVEL, 0);
        MATERIALS.put(Material.GOLD_ORE, 0);
        MATERIALS.put(Material.IRON_ORE, 0);
        MATERIALS.put(Material.COAL_ORE, 0);
        MATERIALS.put(Material.SPONGE, 0);
        MATERIALS.put(Material.GLASS, 0);
        MATERIALS.put(Material.LAPIS_ORE, 0);
        MATERIALS.put(Material.LAPIS_BLOCK, 0);
        MATERIALS.put(Material.DISPENSER, ACTION_ON_RIGHT);
        MATERIALS.put(Material.SANDSTONE, 0);
        MATERIALS.put(Material.NOTE_BLOCK, ACTION_ON_RIGHT);
        MATERIALS.put(Material.POWERED_RAIL, 0);
        MATERIALS.put(Material.DETECTOR_RAIL, 0);
        MATERIALS.put(Material.STICKY_PISTON, 0);
        MATERIALS.put(Material.COBWEB, 0);
        MATERIALS.put(Material.GRASS, 0);

        MATERIALS.put(Material.DEAD_BUSH, 0);
        MATERIALS.put(Material.PISTON, 0);
        MATERIALS.put(Material.PISTON_HEAD, 0);
        MATERIALS.put(Material.MOVING_PISTON, 0);
        MATERIALS.put(Material.SUNFLOWER, 0);
        MATERIALS.put(Material.LILAC, 0);
        MATERIALS.put(Material.PEONY, 0);
        MATERIALS.put(Material.ROSE_BUSH, 0);
        MATERIALS.put(Material.BROWN_MUSHROOM, 0);
        MATERIALS.put(Material.RED_MUSHROOM, 0);
        MATERIALS.put(Material.GOLD_BLOCK, 0);
        MATERIALS.put(Material.IRON_BLOCK, 0);
        MATERIALS.put(Material.BRICK, 0);
        MATERIALS.put(Material.TNT, ACTION_ON_RIGHT);
        MATERIALS.put(Material.BOOKSHELF, 0);
        MATERIALS.put(Material.MOSSY_COBBLESTONE, 0);
        MATERIALS.put(Material.OBSIDIAN, 0);
        MATERIALS.put(Material.TORCH, 0);
        MATERIALS.put(Material.FIRE, 0);
        MATERIALS.put(Material.SPAWNER, ACTION_ON_RIGHT);
        MATERIALS.put(Material.CHEST, ACTION_ON_RIGHT);
        MATERIALS.put(Material.REDSTONE_WIRE, 0);
        MATERIALS.put(Material.DIAMOND_ORE, 0);
        MATERIALS.put(Material.DIAMOND_BLOCK, 0);
        MATERIALS.put(Material.CRAFTING_TABLE, 0);
        MATERIALS.put(Material.WHEAT, 0);
        MATERIALS.put(Material.FARMLAND, 0);
        MATERIALS.put(Material.FURNACE, ACTION_ON_RIGHT);
        MATERIALS.put(Material.LADDER, 0);
        MATERIALS.put(Material.RAIL, 0);
        MATERIALS.put(Material.COBBLESTONE_STAIRS, 0);
        MATERIALS.put(Material.LEVER, ACTION_ON_RIGHT);
        MATERIALS.put(Material.STONE_PRESSURE_PLATE, ACTION_ON_PHYSICAL);
        MATERIALS.put(Material.REDSTONE_ORE, 0);
        MATERIALS.put(Material.REDSTONE_WALL_TORCH, 0);
        MATERIALS.put(Material.REDSTONE_TORCH, 0);
        MATERIALS.put(Material.SNOW, 0);
        MATERIALS.put(Material.ICE, 0);
        MATERIALS.put(Material.SNOW_BLOCK, 0);
        MATERIALS.put(Material.CACTUS, 0);
        MATERIALS.put(Material.CLAY, 0);
        MATERIALS.put(Material.JUKEBOX, ACTION_ON_RIGHT);
        MATERIALS.put(Material.OAK_FENCE, 0);
        MATERIALS.put(Material.PUMPKIN, 0);
        MATERIALS.put(Material.NETHERRACK, 0);
        MATERIALS.put(Material.SOUL_SAND, 0);
        MATERIALS.put(Material.GLOWSTONE, 0);
        MATERIALS.put(Material.NETHER_PORTAL, 0);
        MATERIALS.put(Material.JACK_O_LANTERN, 0);
        MATERIALS.put(Material.CAKE, ACTION_ON_RIGHT);
        MATERIALS.put(Material.REPEATER, ACTION_ON_RIGHT);
//        MATERIALS.put(Material.STAINED_GLASS, 0); // Tag
        MATERIALS.put(Material.ACACIA_TRAPDOOR, ACTION_ON_RIGHT);
        MATERIALS.put(Material.BIRCH_TRAPDOOR, ACTION_ON_RIGHT);
        MATERIALS.put(Material.DARK_OAK_TRAPDOOR, ACTION_ON_RIGHT);
        MATERIALS.put(Material.JUNGLE_TRAPDOOR, ACTION_ON_RIGHT);
        MATERIALS.put(Material.OAK_TRAPDOOR, ACTION_ON_RIGHT);
        MATERIALS.put(Material.SPRUCE_TRAPDOOR, ACTION_ON_RIGHT);
        MATERIALS.put(Material.INFESTED_STONE, 0);
        MATERIALS.put(Material.INFESTED_STONE_BRICKS, 0);
        MATERIALS.put(Material.INFESTED_MOSSY_STONE_BRICKS, 0);
        MATERIALS.put(Material.INFESTED_CRACKED_STONE_BRICKS, 0);
        MATERIALS.put(Material.INFESTED_CHISELED_STONE_BRICKS, 0);
        MATERIALS.put(Material.INFESTED_COBBLESTONE, 0);
        MATERIALS.put(Material.STONE_BRICKS, 0);
        MATERIALS.put(Material.MOSSY_STONE_BRICKS, 0);
        MATERIALS.put(Material.CRACKED_STONE_BRICKS, 0);
        MATERIALS.put(Material.CHISELED_STONE_BRICKS, 0);
        MATERIALS.put(Material.BROWN_MUSHROOM_BLOCK, 0);
        MATERIALS.put(Material.RED_MUSHROOM_BLOCK, 0);
        MATERIALS.put(Material.IRON_BARS, 0);
        MATERIALS.put(Material.GLASS_PANE, 0);
        MATERIALS.put(Material.MELON, 0);
        MATERIALS.put(Material.PUMPKIN_STEM, 0);
        MATERIALS.put(Material.MELON_STEM, 0);
        MATERIALS.put(Material.VINE, 0);
        MATERIALS.put(Material.SPRUCE_FENCE_GATE, ACTION_ON_RIGHT);
        MATERIALS.put(Material.ACACIA_FENCE_GATE, ACTION_ON_RIGHT);
        MATERIALS.put(Material.BIRCH_FENCE_GATE, ACTION_ON_RIGHT);
        MATERIALS.put(Material.DARK_OAK_FENCE_GATE, ACTION_ON_RIGHT);
        MATERIALS.put(Material.JUNGLE_FENCE_GATE, ACTION_ON_RIGHT);
        MATERIALS.put(Material.OAK_FENCE_GATE, ACTION_ON_RIGHT);
        MATERIALS.put(Material.BRICK_STAIRS, 0);
        MATERIALS.put(Material.MYCELIUM, 0);
        MATERIALS.put(Material.LILY_PAD, 0);
        MATERIALS.put(Material.NETHER_BRICK, 0);
        MATERIALS.put(Material.NETHER_BRICK_FENCE, 0);
        MATERIALS.put(Material.NETHER_BRICK_STAIRS, 0);
        MATERIALS.put(Material.ENCHANTING_TABLE, ACTION_ON_RIGHT);
        MATERIALS.put(Material.BREWING_STAND, ACTION_ON_RIGHT);
        MATERIALS.put(Material.CAULDRON, ACTION_ON_RIGHT);
        MATERIALS.put(Material.END_PORTAL, 0);
        MATERIALS.put(Material.END_PORTAL_FRAME, 0);
        MATERIALS.put(Material.END_STONE, 0);
        MATERIALS.put(Material.DRAGON_EGG, ACTION_ON_LEFT | ACTION_ON_RIGHT);
        MATERIALS.put(Material.REDSTONE_LAMP, 0);
        MATERIALS.put(Material.COCOA, 0);
        MATERIALS.put(Material.SANDSTONE_STAIRS, 0);
        MATERIALS.put(Material.EMERALD_ORE, 0);
        MATERIALS.put(Material.ENDER_CHEST, 0);
        MATERIALS.put(Material.TRIPWIRE_HOOK, 0);
        MATERIALS.put(Material.TRIPWIRE, 0);
        MATERIALS.put(Material.EMERALD_BLOCK, 0);
        MATERIALS.put(Material.COMMAND_BLOCK, ACTION_ON_RIGHT);
        MATERIALS.put(Material.BEACON, ACTION_ON_RIGHT);
        MATERIALS.put(Material.ANVIL, ACTION_ON_RIGHT);
        MATERIALS.put(Material.CHIPPED_ANVIL, ACTION_ON_RIGHT);
        MATERIALS.put(Material.DAMAGED_ANVIL, ACTION_ON_RIGHT);
        MATERIALS.put(Material.TRAPPED_CHEST, ACTION_ON_RIGHT);
        MATERIALS.put(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, ACTION_ON_PHYSICAL);
        MATERIALS.put(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, ACTION_ON_PHYSICAL);
        MATERIALS.put(Material.COMPARATOR, ACTION_ON_RIGHT);
        MATERIALS.put(Material.DAYLIGHT_DETECTOR, ACTION_ON_RIGHT);
        MATERIALS.put(Material.REDSTONE_BLOCK, 0);
        MATERIALS.put(Material.NETHER_QUARTZ_ORE, 0);
        MATERIALS.put(Material.HOPPER, ACTION_ON_RIGHT);
        MATERIALS.put(Material.QUARTZ_BLOCK, 0);
        MATERIALS.put(Material.QUARTZ_STAIRS, 0);
        MATERIALS.put(Material.ACTIVATOR_RAIL, 0);
        MATERIALS.put(Material.DROPPER, ACTION_ON_RIGHT);
//        MATERIALS.put(Material.STAINED_CLAY, 0); // Tag
//        MATERIALS.put(Material.STAINED_GLASS_PANE, 0); // Tag
        MATERIALS.put(Material.ACACIA_STAIRS, 0);
        MATERIALS.put(Material.DARK_OAK_STAIRS, 0);
        MATERIALS.put(Material.HAY_BLOCK, 0);
//        MATERIALS.put(Material.HARD_CLAY, 0); // Tag
        MATERIALS.put(Material.COAL_BLOCK, 0);
        MATERIALS.put(Material.PACKED_ICE, 0);
        MATERIALS.put(Material.TALL_GRASS, 0);
        MATERIALS.put(Material.TALL_SEAGRASS, 0);
        MATERIALS.put(Material.LARGE_FERN, 0);

        MATERIALS.put(Material.PRISMARINE, 0);
        MATERIALS.put(Material.SEA_LANTERN, 0);
        MATERIALS.put(Material.SLIME_BLOCK, 0);
        MATERIALS.put(Material.IRON_TRAPDOOR, 0);
        MATERIALS.put(Material.RED_SANDSTONE, 0);
        MATERIALS.put(Material.RED_SANDSTONE_STAIRS, 0);
        MATERIALS.put(Material.SPRUCE_FENCE, 0);
        MATERIALS.put(Material.BIRCH_FENCE, 0);
        MATERIALS.put(Material.JUNGLE_FENCE, 0);
        MATERIALS.put(Material.DARK_OAK_FENCE, 0);
        MATERIALS.put(Material.ACACIA_FENCE, 0);
        MATERIALS.put(Material.SPRUCE_DOOR, ACTION_ON_RIGHT);
        MATERIALS.put(Material.BIRCH_DOOR, ACTION_ON_RIGHT);
        MATERIALS.put(Material.JUNGLE_DOOR, ACTION_ON_RIGHT);
        MATERIALS.put(Material.ACACIA_DOOR, ACTION_ON_RIGHT);
        MATERIALS.put(Material.DARK_OAK_DOOR, ACTION_ON_RIGHT);

        MATERIALS.put(Material.GRASS_PATH, 0);
        MATERIALS.put(Material.CHORUS_PLANT, 0);
        MATERIALS.put(Material.CHORUS_FLOWER, 0);
        MATERIALS.put(Material.BEETROOTS, 0);
        MATERIALS.put(Material.END_ROD, 0);
        MATERIALS.put(Material.END_STONE_BRICKS, 0);
        MATERIALS.put(Material.END_GATEWAY, 0);
        MATERIALS.put(Material.FROSTED_ICE, 0);
        MATERIALS.put(Material.PURPUR_BLOCK, 0);
        MATERIALS.put(Material.PURPUR_STAIRS, 0);
        MATERIALS.put(Material.PURPUR_PILLAR, 0);
        MATERIALS.put(Material.PURPUR_SLAB, 0);
        MATERIALS.put(Material.STRUCTURE_BLOCK, ACTION_ON_LEFT | ACTION_ON_RIGHT);
        MATERIALS.put(Material.REPEATING_COMMAND_BLOCK, ACTION_ON_RIGHT);
        MATERIALS.put(Material.CHAIN_COMMAND_BLOCK , ACTION_ON_RIGHT);

        MATERIALS.put(Material.MAGMA_BLOCK, 0);
        MATERIALS.put(Material.NETHER_WART_BLOCK, 0);
        MATERIALS.put(Material.RED_NETHER_BRICKS, 0);
        MATERIALS.put(Material.BONE_BLOCK, 0);
        MATERIALS.put(Material.BARRIER, 0);
        MATERIALS.put(Material.STRUCTURE_VOID, 0);
        // 1.12
        MATERIALS.put(Material.BLACK_CONCRETE, 0);
        MATERIALS.put(Material.BLUE_CONCRETE, 0);
        MATERIALS.put(Material.BROWN_CONCRETE, 0);
        MATERIALS.put(Material.CYAN_CONCRETE, 0);
        MATERIALS.put(Material.GRAY_CONCRETE, 0);
        MATERIALS.put(Material.GREEN_CONCRETE, 0);
        MATERIALS.put(Material.LIGHT_BLUE_CONCRETE, 0);
        MATERIALS.put(Material.YELLOW_CONCRETE, 0);
        MATERIALS.put(Material.LIGHT_GRAY_CONCRETE, 0);
        MATERIALS.put(Material.LIME_CONCRETE, 0);
        MATERIALS.put(Material.MAGENTA_CONCRETE, 0);
        MATERIALS.put(Material.ORANGE_CONCRETE, 0);
        MATERIALS.put(Material.PINK_CONCRETE, 0);
        MATERIALS.put(Material.PURPLE_CONCRETE, 0);
        MATERIALS.put(Material.RED_CONCRETE, 0);
        MATERIALS.put(Material.WHITE_CONCRETE, 0);
        MATERIALS.put(Material.BLACK_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.BLUE_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.BROWN_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.CYAN_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.GRAY_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.GREEN_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.LIGHT_BLUE_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.YELLOW_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.LIGHT_GRAY_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.LIME_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.MAGENTA_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.ORANGE_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.PINK_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.PURPLE_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.RED_CONCRETE_POWDER, 0);
        MATERIALS.put(Material.WHITE_CONCRETE_POWDER, 0);

        MATERIALS.put(Material.WHITE_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.ORANGE_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.MAGENTA_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.YELLOW_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.LIME_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.PINK_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.GRAY_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.LIGHT_GRAY_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.CYAN_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.PURPLE_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.BLUE_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.BROWN_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.GREEN_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.RED_GLAZED_TERRACOTTA, 0);
        MATERIALS.put(Material.BLACK_GLAZED_TERRACOTTA, 0);

        // 1.13
        MATERIALS.put(Material.ANDESITE, 0);
        MATERIALS.put(Material.ATTACHED_MELON_STEM, 0);
        MATERIALS.put(Material.ATTACHED_PUMPKIN_STEM, 0);
        MATERIALS.put(Material.BLACK_STAINED_GLASS, 0);
        MATERIALS.put(Material.BLACK_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.BLACK_TERRACOTTA, 0);
        MATERIALS.put(Material.BLUE_ICE, 0);
        MATERIALS.put(Material.BLUE_STAINED_GLASS, 0);
        MATERIALS.put(Material.BLUE_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.BLUE_TERRACOTTA, 0);
        MATERIALS.put(Material.BROWN_STAINED_GLASS, 0);
        MATERIALS.put(Material.BROWN_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.BROWN_TERRACOTTA, 0);
        MATERIALS.put(Material.BUBBLE_COLUMN, 0);
        MATERIALS.put(Material.CARROTS, 0);
        MATERIALS.put(Material.CARVED_PUMPKIN, 0);
        MATERIALS.put(Material.CAVE_AIR, 0);
        MATERIALS.put(Material.CHISELED_QUARTZ_BLOCK, 0);
        MATERIALS.put(Material.CHISELED_RED_SANDSTONE, 0);
        MATERIALS.put(Material.CHISELED_SANDSTONE, 0);
        MATERIALS.put(Material.COARSE_DIRT, 0);
        MATERIALS.put(Material.CONDUIT, 0);
        MATERIALS.put(Material.CUT_RED_SANDSTONE, 0);
        MATERIALS.put(Material.CUT_SANDSTONE, 0);
        MATERIALS.put(Material.CYAN_STAINED_GLASS, 0);
        MATERIALS.put(Material.CYAN_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.CYAN_TERRACOTTA, 0);
        MATERIALS.put(Material.DARK_PRISMARINE, 0);
        MATERIALS.put(Material.DIORITE, 0);
        MATERIALS.put(Material.DRIED_KELP_BLOCK, 0);
        MATERIALS.put(Material.FERN, 0);
        MATERIALS.put(Material.GRANITE, 0);
        MATERIALS.put(Material.GRAY_STAINED_GLASS, 0);
        MATERIALS.put(Material.GRAY_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.GRAY_TERRACOTTA, 0);
        MATERIALS.put(Material.GREEN_STAINED_GLASS, 0);
        MATERIALS.put(Material.GREEN_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.GREEN_TERRACOTTA, 0);
        MATERIALS.put(Material.KELP, 0);
        MATERIALS.put(Material.KELP_PLANT, 0);
        MATERIALS.put(Material.LIGHT_BLUE_STAINED_GLASS, 0);
        MATERIALS.put(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.LIGHT_BLUE_TERRACOTTA, 0);
        MATERIALS.put(Material.LIGHT_GRAY_STAINED_GLASS, 0);
        MATERIALS.put(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.LIGHT_GRAY_TERRACOTTA, 0);
        MATERIALS.put(Material.LIME_STAINED_GLASS, 0);
        MATERIALS.put(Material.LIME_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.LIME_TERRACOTTA, 0);
        MATERIALS.put(Material.MAGENTA_STAINED_GLASS, 0);
        MATERIALS.put(Material.MAGENTA_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.MAGENTA_TERRACOTTA, 0);
        MATERIALS.put(Material.MUSHROOM_STEM, 0);
        MATERIALS.put(Material.OBSERVER, 0);
        MATERIALS.put(Material.ORANGE_STAINED_GLASS, 0);
        MATERIALS.put(Material.ORANGE_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.ORANGE_TERRACOTTA, 0);
        MATERIALS.put(Material.PINK_STAINED_GLASS, 0);
        MATERIALS.put(Material.PINK_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.PINK_TERRACOTTA, 0);
        MATERIALS.put(Material.PODZOL, 0);
        MATERIALS.put(Material.POLISHED_ANDESITE, 0);
        MATERIALS.put(Material.POLISHED_DIORITE, 0);
        MATERIALS.put(Material.POLISHED_GRANITE, 0);
        MATERIALS.put(Material.POTATOES, 0);
        MATERIALS.put(Material.PRISMARINE_BRICKS, 0);
        MATERIALS.put(Material.PURPLE_STAINED_GLASS, 0);
        MATERIALS.put(Material.PURPLE_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.PURPLE_TERRACOTTA, 0);
        MATERIALS.put(Material.QUARTZ_PILLAR, 0);
        MATERIALS.put(Material.RED_SAND, 0);
        MATERIALS.put(Material.RED_STAINED_GLASS, 0);
        MATERIALS.put(Material.RED_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.RED_TERRACOTTA, 0);
        MATERIALS.put(Material.SEAGRASS, 0);
        MATERIALS.put(Material.SEA_PICKLE, 0);
        MATERIALS.put(Material.SMOOTH_QUARTZ, 0);
        MATERIALS.put(Material.SMOOTH_RED_SANDSTONE, 0);
        MATERIALS.put(Material.SMOOTH_SANDSTONE, 0);
        MATERIALS.put(Material.SMOOTH_STONE, 0);
        MATERIALS.put(Material.TERRACOTTA, 0);
        MATERIALS.put(Material.TURTLE_EGG, 0);
        MATERIALS.put(Material.VOID_AIR, 0);
        MATERIALS.put(Material.WALL_TORCH, 0);
        MATERIALS.put(Material.WET_SPONGE, 0);
        MATERIALS.put(Material.WHITE_STAINED_GLASS, 0);
        MATERIALS.put(Material.WHITE_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.WHITE_TERRACOTTA, 0);
        MATERIALS.put(Material.YELLOW_STAINED_GLASS, 0);
        MATERIALS.put(Material.YELLOW_STAINED_GLASS_PANE, 0);
        MATERIALS.put(Material.YELLOW_TERRACOTTA, 0);

        MATERIALS.put(Material.BAMBOO, 0);
        MATERIALS.put(Material.BAMBOO_SAPLING, 0);
        MATERIALS.put(Material.BARREL, ACTION_ON_RIGHT);
        MATERIALS.put(Material.BELL, ACTION_ON_RIGHT);
        MATERIALS.put(Material.BLAST_FURNACE, ACTION_ON_RIGHT);
        MATERIALS.put(Material.CAMPFIRE, ACTION_ON_RIGHT | ACTION_ON_LEFT);
        MATERIALS.put(Material.CARTOGRAPHY_TABLE, 0);
        MATERIALS.put(Material.COMPOSTER, ACTION_ON_RIGHT);
        MATERIALS.put(Material.FLETCHING_TABLE, 0);
        MATERIALS.put(Material.GRINDSTONE, 0);
        MATERIALS.put(Material.JIGSAW, ACTION_ON_RIGHT | ACTION_ON_LEFT);
        MATERIALS.put(Material.LANTERN, 0);
        MATERIALS.put(Material.LECTERN, 0);
        MATERIALS.put(Material.LOOM, 0);
        MATERIALS.put(Material.SCAFFOLDING, 0);
        MATERIALS.put(Material.SMITHING_TABLE, 0);
        MATERIALS.put(Material.SMOKER, ACTION_ON_RIGHT);
        MATERIALS.put(Material.STONECUTTER, 0);
        MATERIALS.put(Material.SWEET_BERRY_BUSH, ACTION_ON_RIGHT);

        MATERIALS.put(Material.IRON_SHOVEL, EXECUTES_ACTION);
        MATERIALS.put(Material.IRON_PICKAXE, 0);
        MATERIALS.put(Material.IRON_AXE, EXECUTES_ACTION);
        MATERIALS.put(Material.FLINT_AND_STEEL, 0);
        MATERIALS.put(Material.APPLE, 0);
        MATERIALS.put(Material.BOW, 0);
        MATERIALS.put(Material.ARROW, 0);
        MATERIALS.put(Material.COAL, 0);
        MATERIALS.put(Material.DIAMOND, 0);
        MATERIALS.put(Material.IRON_INGOT, 0);
        MATERIALS.put(Material.GOLD_INGOT, 0);
        MATERIALS.put(Material.IRON_SWORD, 0);
        MATERIALS.put(Material.WOODEN_SWORD, 0);
        MATERIALS.put(Material.WOODEN_SHOVEL, EXECUTES_ACTION);
        MATERIALS.put(Material.WOODEN_PICKAXE, 0);
        MATERIALS.put(Material.WOODEN_AXE, EXECUTES_ACTION);
        MATERIALS.put(Material.STONE_SWORD, 0);
        MATERIALS.put(Material.STONE_SHOVEL, EXECUTES_ACTION);
        MATERIALS.put(Material.STONE_PICKAXE, 0);
        MATERIALS.put(Material.STONE_AXE, EXECUTES_ACTION);
        MATERIALS.put(Material.DIAMOND_SWORD, 0);
        MATERIALS.put(Material.DIAMOND_SHOVEL, EXECUTES_ACTION);
        MATERIALS.put(Material.DIAMOND_PICKAXE, 0);
        MATERIALS.put(Material.DIAMOND_AXE, EXECUTES_ACTION);
        MATERIALS.put(Material.STICK, 0);
        MATERIALS.put(Material.BOWL, 0);
        MATERIALS.put(Material.MUSHROOM_STEW, 0);
        MATERIALS.put(Material.GOLDEN_SWORD, 0);
        MATERIALS.put(Material.GOLDEN_SHOVEL, EXECUTES_ACTION);
        MATERIALS.put(Material.GOLDEN_PICKAXE, 0);
        MATERIALS.put(Material.GOLDEN_AXE, EXECUTES_ACTION);
        MATERIALS.put(Material.STRING, 0);
        MATERIALS.put(Material.FEATHER, 0);
        MATERIALS.put(Material.GUNPOWDER, 0);
        MATERIALS.put(Material.WOODEN_HOE, EXECUTES_ACTION);
        MATERIALS.put(Material.STONE_HOE, EXECUTES_ACTION);
        MATERIALS.put(Material.IRON_HOE, EXECUTES_ACTION);
        MATERIALS.put(Material.DIAMOND_HOE, EXECUTES_ACTION);
        MATERIALS.put(Material.GOLDEN_HOE, EXECUTES_ACTION);
        MATERIALS.put(Material.WHEAT_SEEDS, 0);
        MATERIALS.put(Material.BREAD, 0);
        MATERIALS.put(Material.LEATHER_HELMET, 0);
        MATERIALS.put(Material.LEATHER_CHESTPLATE, 0);
        MATERIALS.put(Material.LEATHER_LEGGINGS, 0);
        MATERIALS.put(Material.LEATHER_BOOTS, 0);
        MATERIALS.put(Material.CHAINMAIL_HELMET, 0);
        MATERIALS.put(Material.CHAINMAIL_CHESTPLATE, 0);
        MATERIALS.put(Material.CHAINMAIL_LEGGINGS, 0);
        MATERIALS.put(Material.CHAINMAIL_BOOTS, 0);
        MATERIALS.put(Material.IRON_HELMET, 0);
        MATERIALS.put(Material.IRON_CHESTPLATE, 0);
        MATERIALS.put(Material.IRON_LEGGINGS, 0);
        MATERIALS.put(Material.IRON_BOOTS, 0);
        MATERIALS.put(Material.DIAMOND_HELMET, 0);
        MATERIALS.put(Material.DIAMOND_CHESTPLATE, 0);
        MATERIALS.put(Material.DIAMOND_LEGGINGS, 0);
        MATERIALS.put(Material.DIAMOND_BOOTS, 0);
        MATERIALS.put(Material.GOLDEN_HELMET, 0);
        MATERIALS.put(Material.GOLDEN_CHESTPLATE, 0);
        MATERIALS.put(Material.GOLDEN_LEGGINGS, 0);
        MATERIALS.put(Material.GOLDEN_BOOTS, 0);
        MATERIALS.put(Material.FLINT, 0);
        MATERIALS.put(Material.PORKCHOP, 0);
        MATERIALS.put(Material.COOKED_PORKCHOP, 0);
        MATERIALS.put(Material.PAINTING, 0);
        MATERIALS.put(Material.GOLDEN_APPLE, 0);
        MATERIALS.put(Material.BUCKET, 0);
        MATERIALS.put(Material.WATER_BUCKET, 0);
        MATERIALS.put(Material.LAVA_BUCKET, 0);
        MATERIALS.put(Material.MINECART, 0);
        MATERIALS.put(Material.SADDLE, 0);
        MATERIALS.put(Material.IRON_DOOR, 0);
        MATERIALS.put(Material.REDSTONE, 0);
        MATERIALS.put(Material.SNOWBALL, 0);

        MATERIALS.put(Material.LEATHER, 0);
        MATERIALS.put(Material.MILK_BUCKET, 0);
        MATERIALS.put(Material.BRICKS, 0);
        MATERIALS.put(Material.CLAY_BALL, 0);
        MATERIALS.put(Material.SUGAR_CANE, 0);
        MATERIALS.put(Material.PAPER, 0);
        MATERIALS.put(Material.BOOK, 0);
        MATERIALS.put(Material.SLIME_BALL, 0);
        MATERIALS.put(Material.CHEST_MINECART, 0);
        MATERIALS.put(Material.FURNACE_MINECART, 0);
        MATERIALS.put(Material.EGG, 0);
        MATERIALS.put(Material.COMPASS, 0);
        MATERIALS.put(Material.FISHING_ROD, 0);
        MATERIALS.put(Material.CLOCK, 0);
        MATERIALS.put(Material.GLOWSTONE_DUST, 0);
        MATERIALS.put(Material.COD, 0);
        MATERIALS.put(Material.COOKED_COD, 0);
        MATERIALS.put(Material.INK_SAC, 0);
        MATERIALS.put(Material.BLACK_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.BLUE_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.BROWN_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.CYAN_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.GRAY_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.GREEN_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.LIGHT_BLUE_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.LIGHT_GRAY_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.LIME_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.MAGENTA_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.ORANGE_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.PINK_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.PURPLE_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.RED_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.WHITE_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.YELLOW_DYE, EXECUTES_ACTION);
        MATERIALS.put(Material.COCOA_BEANS, 0);
        MATERIALS.put(Material.BONE_MEAL, EXECUTES_ACTION);
        MATERIALS.put(Material.BONE, 0);
        MATERIALS.put(Material.SUGAR, 0);
        MATERIALS.put(Material.COOKIE, 0);
        MATERIALS.put(Material.MAP, 0);
        MATERIALS.put(Material.SHEARS, EXECUTES_ACTION);
        MATERIALS.put(Material.MELON_SLICE, 0);
        MATERIALS.put(Material.PUMPKIN_SEEDS, 0);
        MATERIALS.put(Material.MELON_SEEDS, 0);
        MATERIALS.put(Material.BEEF, 0);
        MATERIALS.put(Material.COOKED_BEEF, 0);
        MATERIALS.put(Material.CHICKEN, 0);
        MATERIALS.put(Material.COOKED_CHICKEN, 0);
        MATERIALS.put(Material.ROTTEN_FLESH, 0);
        MATERIALS.put(Material.ENDER_PEARL, 0);
        MATERIALS.put(Material.BLAZE_ROD, 0);
        MATERIALS.put(Material.GHAST_TEAR, 0);
        MATERIALS.put(Material.GOLD_NUGGET, 0);
        MATERIALS.put(Material.NETHER_WART, 0);
        MATERIALS.put(Material.POTION, 0);
        MATERIALS.put(Material.GLASS_BOTTLE, 0);
        MATERIALS.put(Material.SPIDER_EYE, 0);
        MATERIALS.put(Material.FERMENTED_SPIDER_EYE, 0);
        MATERIALS.put(Material.BLAZE_POWDER, 0);
        MATERIALS.put(Material.MAGMA_CREAM, 0);
        MATERIALS.put(Material.ENDER_EYE, 0);
        MATERIALS.put(Material.GLISTERING_MELON_SLICE, 0);
        MATERIALS.put(Material.EXPERIENCE_BOTTLE, 0);
        MATERIALS.put(Material.FIRE_CHARGE, 0);
        MATERIALS.put(Material.WRITABLE_BOOK, 0);
        MATERIALS.put(Material.WRITTEN_BOOK, 0);
        MATERIALS.put(Material.EMERALD, 0);
        MATERIALS.put(Material.ITEM_FRAME, 0);
        MATERIALS.put(Material.CARROT, 0);
        MATERIALS.put(Material.POTATO, 0);
        MATERIALS.put(Material.BAKED_POTATO, 0);
        MATERIALS.put(Material.POISONOUS_POTATO, 0);
        MATERIALS.put(Material.FILLED_MAP, 0);
        MATERIALS.put(Material.GOLDEN_CARROT, 0);
        MATERIALS.put(Material.CREEPER_HEAD, 0);
        MATERIALS.put(Material.CREEPER_WALL_HEAD, 0);
        MATERIALS.put(Material.DRAGON_HEAD, 0);
        MATERIALS.put(Material.DRAGON_WALL_HEAD, 0);
        MATERIALS.put(Material.PLAYER_HEAD, 0);
        MATERIALS.put(Material.PLAYER_WALL_HEAD, 0);
        MATERIALS.put(Material.ZOMBIE_HEAD, 0);
        MATERIALS.put(Material.ZOMBIE_WALL_HEAD, 0);
        MATERIALS.put(Material.SKELETON_SKULL, 0);
        MATERIALS.put(Material.SKELETON_WALL_SKULL, 0);
        MATERIALS.put(Material.WITHER_SKELETON_SKULL, 0);
        MATERIALS.put(Material.WITHER_SKELETON_WALL_SKULL, 0);
        MATERIALS.put(Material.CARROT_ON_A_STICK, 0);
        MATERIALS.put(Material.NETHER_STAR, 0);
        MATERIALS.put(Material.PUMPKIN_PIE, 0);
        MATERIALS.put(Material.FIREWORK_ROCKET, 0);
        MATERIALS.put(Material.FIREWORK_STAR, 0);
        MATERIALS.put(Material.ENCHANTED_BOOK, 0);
        MATERIALS.put(Material.NETHER_BRICKS, 0);
        MATERIALS.put(Material.QUARTZ, 0);
        MATERIALS.put(Material.TNT_MINECART, 0);
        MATERIALS.put(Material.HOPPER_MINECART, 0);
        MATERIALS.put(Material.LEAD, 0);
        MATERIALS.put(Material.NAME_TAG, 0);
        MATERIALS.put(Material.COMMAND_BLOCK_MINECART, 0);

        MATERIALS.put(Material.PRISMARINE_SHARD, 0);
        MATERIALS.put(Material.PRISMARINE_CRYSTALS, 0);
        MATERIALS.put(Material.RABBIT, 0);
        MATERIALS.put(Material.COOKED_RABBIT, 0);
        MATERIALS.put(Material.RABBIT_STEW, 0);
        MATERIALS.put(Material.RABBIT_FOOT, 0);
        MATERIALS.put(Material.RABBIT_HIDE, 0);
        MATERIALS.put(Material.ARMOR_STAND, 0);
        MATERIALS.put(Material.LEATHER_HORSE_ARMOR, 0);
        MATERIALS.put(Material.IRON_HORSE_ARMOR, 0);
        MATERIALS.put(Material.GOLDEN_HORSE_ARMOR, 0);
        MATERIALS.put(Material.DIAMOND_HORSE_ARMOR, 0);
        MATERIALS.put(Material.MUTTON, 0);
        MATERIALS.put(Material.COOKED_MUTTON, 0);

        MATERIALS.put(Material.BEETROOT, 0);
        MATERIALS.put(Material.BEETROOT_SOUP, 0);
        MATERIALS.put(Material.BEETROOT_SEEDS, 0);
        MATERIALS.put(Material.CHORUS_FRUIT, 0);
        MATERIALS.put(Material.POPPED_CHORUS_FRUIT, 0);
        MATERIALS.put(Material.SHIELD, 0);
        MATERIALS.put(Material.SPECTRAL_ARROW, 0);
        MATERIALS.put(Material.TIPPED_ARROW, 0);
        MATERIALS.put(Material.DRAGON_BREATH, 0);
        MATERIALS.put(Material.LINGERING_POTION, 0);
        MATERIALS.put(Material.ELYTRA, 0);
        MATERIALS.put(Material.END_CRYSTAL, 0);

        MATERIALS.put(Material.TOTEM_OF_UNDYING, 0);
        MATERIALS.put(Material.SHULKER_SHELL, 0);
        MATERIALS.put(Material.KNOWLEDGE_BOOK, 0);

        MATERIALS.put(Material.CHARCOAL, 0);
        MATERIALS.put(Material.COD_BUCKET, 0);
        MATERIALS.put(Material.COOKED_SALMON, 0);
        MATERIALS.put(Material.DEBUG_STICK, 0);
        MATERIALS.put(Material.DRIED_KELP, 0);
        MATERIALS.put(Material.ENCHANTED_GOLDEN_APPLE, 0);
        MATERIALS.put(Material.HEART_OF_THE_SEA, 0);
        MATERIALS.put(Material.IRON_NUGGET, 0);
        MATERIALS.put(Material.LAPIS_LAZULI, 0);
        MATERIALS.put(Material.NAUTILUS_SHELL, 0);
        MATERIALS.put(Material.PHANTOM_MEMBRANE, 0);
        MATERIALS.put(Material.PUFFERFISH, 0);
        MATERIALS.put(Material.PUFFERFISH_BUCKET, 0);
        MATERIALS.put(Material.SALMON, 0);
        MATERIALS.put(Material.SALMON_BUCKET, 0);
        MATERIALS.put(Material.SCUTE, 0);
        MATERIALS.put(Material.SPLASH_POTION, 0);
        MATERIALS.put(Material.TURTLE_HELMET, 0);
        MATERIALS.put(Material.TRIDENT, 0);
        MATERIALS.put(Material.TROPICAL_FISH, 0);
        MATERIALS.put(Material.TROPICAL_FISH_BUCKET, 0);

        MATERIALS.put(Material.CREEPER_BANNER_PATTERN, 0);
        MATERIALS.put(Material.FLOWER_BANNER_PATTERN, 0);
        MATERIALS.put(Material.GLOBE_BANNER_PATTERN, 0);
        MATERIALS.put(Material.MOJANG_BANNER_PATTERN, 0);
        MATERIALS.put(Material.SKULL_BANNER_PATTERN, 0);
        MATERIALS.put(Material.CROSSBOW, 0);
        MATERIALS.put(Material.SUSPICIOUS_STEW, 0);
        MATERIALS.put(Material.SWEET_BERRIES, 0);

        // 1.15
        MATERIALS.put(Material.BEEHIVE, ACTION_ON_RIGHT);
        MATERIALS.put(Material.BEE_NEST, ACTION_ON_RIGHT);
        MATERIALS.put(Material.HONEY_BLOCK, 0);
        MATERIALS.put(Material.HONEYCOMB_BLOCK, 0);
        MATERIALS.put(Material.HONEY_BOTTLE, 0);
        MATERIALS.put(Material.HONEYCOMB, 0);


        // Tags
        Tag.DOORS.getValues().forEach(door -> MATERIALS.put(door, ACTION_ON_RIGHT));

        Tag.ITEMS_BOATS.getValues().forEach(boat -> MATERIALS.put(boat, 0));

        Tag.BANNERS.getValues().forEach(banner -> MATERIALS.put(banner, 0));

        Tag.SLABS.getValues().forEach(slab -> MATERIALS.put(slab, 0));

        Tag.PLANKS.getValues().forEach(plank -> MATERIALS.put(plank, 0));

        Tag.CARPETS.getValues().forEach(carpet -> MATERIALS.put(carpet, 0));

        Tag.SAPLINGS.getValues().forEach(sapling -> MATERIALS.put(sapling, 0));

        Tag.LOGS.getValues().forEach(log -> MATERIALS.put(log, 0));

        Tag.LEAVES.getValues().forEach(leaf -> MATERIALS.put(leaf, 0));

        Tag.STAIRS.getValues().forEach(stair -> MATERIALS.put(stair, 0));

        Tag.WOOL.getValues().forEach(wool -> MATERIALS.put(wool, 0));

        Tag.WOODEN_PRESSURE_PLATES.getValues().forEach(pressure -> MATERIALS.put(pressure, ACTION_ON_PHYSICAL));

        Tag.BUTTONS.getValues().forEach(button -> MATERIALS.put(button, ACTION_ON_RIGHT));

        Tag.FLOWER_POTS.getValues().forEach(pot -> MATERIALS.put(pot, ACTION_ON_RIGHT));

        Tag.WALLS.getValues().forEach(wall -> MATERIALS.put(wall, 0));

        Tag.SIGNS.getValues().forEach(sign -> MATERIALS.put(sign, 0));

        Tag.SMALL_FLOWERS.getValues().forEach(flower -> MATERIALS.put(flower, 0));

        Tag.BEDS.getValues().forEach(bed -> MATERIALS.put(bed, ACTION_ON_RIGHT));

        Tag.ITEMS_MUSIC_DISCS.getValues().forEach(disc -> MATERIALS.put(disc, 0));

        Tag.ITEMS_BANNERS.getValues().forEach(banner -> MATERIALS.put(banner, 0));

        Stream.concat(
                Stream.concat(
                        Tag.CORAL_BLOCKS.getValues().stream(),
                        Tag.CORALS.getValues().stream()
                ),
                Tag.WALL_CORALS.getValues().stream()
        ).forEach(m -> {
            MATERIALS.put(m, 0);
            Optional.ofNullable(Material.getMaterial("DEAD_" + m.name())).ifPresent(mat -> MATERIALS.put(mat, 0));
        });

        Arrays.stream(Material.values()).forEach(m -> {
            if (m.isLegacy()) return;
            if (isSpawnEgg(m)) MATERIALS.put(m, EXECUTES_ACTION);
            if (SHULKER_BOXES.contains(m)) MATERIALS.put(m, ACTION_ON_RIGHT);
            if (!MATERIALS.containsKey(m)) {
                System.out.println("MISSING: " + m.name());
            }
        });
    }

    private Materials() { }

    public static Material getRelated(EntityType entity) {
        return ENTITY_MAP.get(entity);
    }

    public static EntityType getRelated(Material material) {
        return ENTITY_MAP.inverse().get(material);
    }

    public static boolean isLighter(Material material) {
        return material == Material.FLINT_AND_STEEL || material == Material.FIRE_CHARGE;
    }

    public static boolean isSpawnEgg(Material material) {
        return material.name().endsWith("_SPAWN_EGG");
    }

    public static boolean isInventory(Material material) {
        switch (material) {
            case CHEST:
            case JUKEBOX:
            case DISPENSER:
            case FURNACE:
            case BREWING_STAND:
            case TRAPPED_CHEST:
            case HOPPER:
            case DROPPER:
            case BARREL:
            case BLAST_FURNACE:
            case SMOKER:
                return true;
            default:
                return SHULKER_BOXES.contains(material);
        }
    }

    public static boolean isModOnClick(Material material, Action action) {
        Integer flags = MATERIALS.get(material);
        return flags == null
                || (action == Action.RIGHT_CLICK_BLOCK && (flags & ACTION_ON_RIGHT) == ACTION_ON_RIGHT)
                || (action == Action.LEFT_CLICK_BLOCK && (flags & ACTION_ON_LEFT) == ACTION_ON_LEFT)
                || (action == Action.PHYSICAL && (flags & ACTION_ON_PHYSICAL) == ACTION_ON_PHYSICAL);
    }

    public static boolean isItemAppliedToBlock(Material item, Material block) {
        Integer flags = MATERIALS.get(item);
        return flags == null || (flags & EXECUTES_ACTION) == EXECUTES_ACTION;
    }

    public static void load() {
        //
    }
}
