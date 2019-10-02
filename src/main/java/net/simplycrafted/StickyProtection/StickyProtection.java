package net.simplycrafted.StickyProtection;

import net.simplycrafted.StickyLocks.Database;
import net.simplycrafted.StickyLocks.StickyLocks;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.material.SimpleAttachableMaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

/**
 * Copyright © Brian Ronald
 * 04/08/14
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
public class StickyProtection extends JavaPlugin implements Listener {
    StickyLocks stickyLocks;
    Database database;

    @Override
    public void onEnable() {
        stickyLocks = (StickyLocks) getServer().getPluginManager().getPlugin("StickyLocks");
        database = new Database();
        getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {

    }

    boolean checkProtection(Block block, Player player) {
        // Check whether this block is protected by StickyLocks
        if (accessDenied(block, player)) {
            return true;
        }
        // Check whether this block is supporting a block above it which is protected by StickyLocks
        Block relativeBlock = block.getRelative(BlockFace.UP);
        switch (relativeBlock.getType()) {
            case IRON_DOOR:
            case ACACIA_DOOR:
            case BIRCH_DOOR:
            case DARK_OAK_DOOR:
            case JUNGLE_DOOR:
            case OAK_DOOR:
            case SPRUCE_DOOR:
            case COMPARATOR:
            case REPEATER:
            case SIGN:
            case DRAGON_EGG:
            case ANVIL:
            case CHIPPED_ANVIL:
            case DAMAGED_ANVIL:
            case ACACIA_PRESSURE_PLATE:
            case BIRCH_PRESSURE_PLATE:
            case DARK_OAK_PRESSURE_PLATE:
            case HEAVY_WEIGHTED_PRESSURE_PLATE:
            case JUNGLE_PRESSURE_PLATE:
            case LIGHT_WEIGHTED_PRESSURE_PLATE:
            case OAK_PRESSURE_PLATE:
            case SPRUCE_PRESSURE_PLATE:
            case STONE_PRESSURE_PLATE:
            case GRAVEL:
            case SAND:
            case REDSTONE_WIRE:
            case BREWING_STAND:
                if(accessDenied(relativeBlock, player)) {
                    return true;
                }
                break;
            case TORCH:
            case REDSTONE_TORCH:
            case ACACIA_BUTTON:
            case BIRCH_BUTTON:
            case DARK_OAK_BUTTON:
            case JUNGLE_BUTTON:
            case OAK_BUTTON:
            case SPRUCE_BUTTON:
            case STONE_BUTTON:
            case LEVER:
                try {
                    if (((SimpleAttachableMaterialData) relativeBlock.getState().getData()).getAttachedFace().equals(BlockFace.DOWN) & accessDenied(relativeBlock, player)) {
                        return true;
                    }
                } catch (ClassCastException e) {
                    getLogger().info("ClassCastException (safe to ignore)");
                }
        }
        // This is interesting... an anonymous set to iterate, so I don't have to write code four times.
        for (BlockFace lateralFace : new HashSet<BlockFace>(){{
            add(BlockFace.EAST);
            add(BlockFace.NORTH);
            add(BlockFace.WEST);
            add(BlockFace.SOUTH);
        }}) {
            // Things which hang off the side of a block
            relativeBlock = block.getRelative(lateralFace);
            switch (relativeBlock.getType()) {
                case WALL_SIGN:
                case TORCH:
                case REDSTONE_TORCH:
                case ACACIA_BUTTON:
                case BIRCH_BUTTON:
                case DARK_OAK_BUTTON:
                case JUNGLE_BUTTON:
                case OAK_BUTTON:
                case SPRUCE_BUTTON:
                case STONE_BUTTON:
                case LEVER:
                case LADDER:
                case TRIPWIRE_HOOK:
                    try {
                        if (((SimpleAttachableMaterialData) relativeBlock.getState().getData()).getAttachedFace().equals(lateralFace.getOppositeFace()) & accessDenied(relativeBlock, player)) {
                            return true;
                        }
                    } catch (ClassCastException e) {
                        getLogger().info("ClassCastException (safe to ignore)");
                    }
            }
            // Things which hang from under a block
            relativeBlock = block.getRelative(BlockFace.DOWN);
            switch (relativeBlock.getType()) {
                case ACACIA_BUTTON:
                case BIRCH_BUTTON:
                case DARK_OAK_BUTTON:
                case JUNGLE_BUTTON:
                case OAK_BUTTON:
                case SPRUCE_BUTTON:
                case STONE_BUTTON:
                case LEVER:
                    try {
                        if (((SimpleAttachableMaterialData) relativeBlock.getState().getData()).getAttachedFace().equals(BlockFace.UP) & accessDenied(relativeBlock, player)) {
                            return true;
                        }
                    } catch (ClassCastException e) {
                        getLogger().info("ClassCastException (safe to ignore)");
                    }
            }
        }
        return false;
    }

    boolean checkProtection(Block block) {
        // Check whether this block is protected by StickyLocks
        if (accessDenied(block)) {
            return true;
        }
        // Check whether this block is supporting a block above it which is protected by StickyLocks
        Block relativeBlock = block.getRelative(BlockFace.UP);
        switch (relativeBlock.getType()) {
            case IRON_DOOR:
            case ACACIA_DOOR:
            case BIRCH_DOOR:
            case DARK_OAK_DOOR:
            case JUNGLE_DOOR:
            case OAK_DOOR:
            case SPRUCE_DOOR:
            case COMPARATOR:
            case REPEATER:
            case SIGN:
            case DRAGON_EGG:
            case ANVIL:
            case ACACIA_PRESSURE_PLATE:
            case BIRCH_PRESSURE_PLATE:
            case DARK_OAK_PRESSURE_PLATE:
            case HEAVY_WEIGHTED_PRESSURE_PLATE:
            case JUNGLE_PRESSURE_PLATE:
            case LIGHT_WEIGHTED_PRESSURE_PLATE:
            case OAK_PRESSURE_PLATE:
            case SPRUCE_PRESSURE_PLATE:
            case STONE_PRESSURE_PLATE:
            case GRAVEL:
            case SAND:
            case REDSTONE_WIRE:
                if(accessDenied(relativeBlock)) {
                    return true;
                }
                break;
            case TORCH:
            case REDSTONE_TORCH:
            case ACACIA_BUTTON:
            case BIRCH_BUTTON:
            case DARK_OAK_BUTTON:
            case JUNGLE_BUTTON:
            case OAK_BUTTON:
            case SPRUCE_BUTTON:
            case STONE_BUTTON:
            case LEVER:
                try {
                    if (((SimpleAttachableMaterialData) relativeBlock.getState().getData()).getAttachedFace().equals(BlockFace.DOWN) & accessDenied(relativeBlock)) {
                        return true;
                    }
                } catch (ClassCastException e) {
                    getLogger().info("ClassCastException (safe to ignore)");
                }
        }
        // This is interesting... an anonymous set to iterate, so I don't have to write code four times.
        for (BlockFace lateralFace : new HashSet<BlockFace>(){{
            add(BlockFace.EAST);
            add(BlockFace.NORTH);
            add(BlockFace.WEST);
            add(BlockFace.SOUTH);
        }}) {
            // Things which hang off the side of a block
            relativeBlock = block.getRelative(lateralFace);
            switch (relativeBlock.getType()) {
                case WALL_SIGN:
                case TORCH:
                case REDSTONE_TORCH:
                case ACACIA_BUTTON:
                case BIRCH_BUTTON:
                case DARK_OAK_BUTTON:
                case JUNGLE_BUTTON:
                case OAK_BUTTON:
                case SPRUCE_BUTTON:
                case STONE_BUTTON:
                case LEVER:
                case LADDER:
                case TRIPWIRE_HOOK:
                    try {
                        if (((SimpleAttachableMaterialData) relativeBlock.getState().getData()).getAttachedFace().equals(lateralFace.getOppositeFace()) & accessDenied(relativeBlock)) {
                            return true;
                        }
                    } catch (ClassCastException e) {
                        getLogger().info("ClassCastException (safe to ignore)");
                    }
            }
            // Things which hang from under a block
            relativeBlock = block.getRelative(BlockFace.DOWN);
            switch (relativeBlock.getType()) {
                case ACACIA_BUTTON:
                case BIRCH_BUTTON:
                case DARK_OAK_BUTTON:
                case JUNGLE_BUTTON:
                case OAK_BUTTON:
                case SPRUCE_BUTTON:
                case STONE_BUTTON:
                case LEVER:
                    try {
                        if (((SimpleAttachableMaterialData) relativeBlock.getState().getData()).getAttachedFace().equals(BlockFace.UP) & accessDenied(relativeBlock)) {
                            return true;
                        }
                    } catch (ClassCastException e) {
                        getLogger().info("ClassCastException (safe to ignore)");
                    }
            }
        }
        return false;
    }

    private boolean accessDenied(Block block, Player player) {
        return database.getProtection(block).isProtected() && database.accessDenied(player, block);
    }

    private boolean accessDenied(Block block) {
        return database.getProtection(block).isProtected();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (checkProtection(event.getBlock(),event.getPlayer())) {
            if (event.getPlayer()!= null && !event.getPlayer().hasPermission("stickylocks.ghost")) {
                event.setCancelled(true);
                stickyLocks.sendMessage(event.getPlayer(),"This block is protected from destruction",false);
            }
        }
    }

    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        if (event.isCancelled()) {
            return;
        }
        for (Block block : event.getBlocks()) {
            if (checkProtection(block)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        if (event.isCancelled() | !event.isSticky()) {
            return;
        }
        // Check the block beyond the piston extension, which is two blocks away
        if (checkProtection(event.getBlock().getRelative(event.getDirection()).getRelative(event.getDirection()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (checkProtection(event.getBlock())) {
            event.setCancelled(true);
        }
    }
}
