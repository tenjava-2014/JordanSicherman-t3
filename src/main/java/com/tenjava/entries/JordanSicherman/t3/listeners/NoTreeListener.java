/**
 * 
 */
package com.tenjava.entries.JordanSicherman.t3.listeners;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

/**
 * @author Jordan
 * 
 */
public class NoTreeListener implements Listener {

	@EventHandler
	private void onChunkLoad(ChunkLoadEvent e) {
		Chunk chunk = e.getChunk();
		World world = e.getWorld();

		int blockX = chunk.getX() << 4;
		int blockZ = chunk.getZ() << 4;

		// Lots of diamonds but no wood makes Bill a sad, sad boy.
		for (int x = blockX; x < blockX + 16; x++) {
			for (int z = blockZ; z < blockZ + 16; z++) {
				for (int y = 0; y < world.getMaxHeight(); y++) {
					Block block = world.getBlockAt(x, y, z);
					if (block.getTypeId() == Material.LOG.getId() || block.getTypeId() == Material.LOG_2.getId()) {
						block.setType(Material.DIAMOND_BLOCK);
					}
				}
			}
		}
	}
}
