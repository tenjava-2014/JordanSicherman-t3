/**
 * 
 */
package com.tenjava.entries.JordanSicherman.t3.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.tenjava.entries.JordanSicherman.t3.RandomManager;

/**
 * @author Jordan
 * 
 */
public class FallingStarListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onStarLand(ProjectileHitEvent e) {
		if (e.getEntity().hasMetadata("FALLING_STAR")) {
			Location centre = e.getEntity().getLocation();
			World world = centre.getWorld();
			int blockX = centre.getBlockX();
			int blockZ = centre.getBlockZ();
			int power = 10;
			for (int x = -power; x < power; x++) {
				for (int z = -power; z < power; z++) {
					if (x * x + z * z > 100) {
						continue;
					}
					int y = world.getHighestBlockYAt(blockX + x, blockZ + z);
					world.getBlockAt(blockX + x, y, blockZ + z).setType(randomStarMaterial());
				}
			}
		}
	}

	private Material randomStarMaterial() {
		int choice = (int) RandomManager.getRandomInRange(0, 100);
		if (choice < 2) {
			return Material.DIAMOND_ORE;
		} else if (choice < 10) {
			return Material.REDSTONE_ORE;
		} else if (choice < 20) {
			return Material.IRON_ORE;
		} else if (choice < 39) {
			return Material.GOLD_ORE;
		} else if (choice < 62) {
			return Material.COAL_ORE;
		} else if (choice < 69) {
			return Material.OBSIDIAN;
		} else if (choice < 75) {
			return Material.EMERALD_ORE;
		} else if (choice < 78) { return Material.PACKED_ICE; }
		return Material.STONE;
	}
}
