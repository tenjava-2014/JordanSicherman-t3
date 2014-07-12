/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3.events;

import main.java.com.tenjava.entries.JordanSicherman.t3.RandomManager;
import main.java.com.tenjava.entries.JordanSicherman.t3.TenJava;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 *         A forest fire event. Ignites a nearby tree with lightning and allows
 *         it to run its course.
 */
public class ForestFireEvent extends RandomEvent {

	private transient Location initializer;

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#start()
	 */
	@Override
	public void start() {
		if (!canStart()) {
			TenJava.log("A forest fire event attempted to, and failed, to begin.");
			return;
		}

		smiteNearbyBlocks(initializer, 30, Material.LEAVES, 20);
		TenJava.log("A forest fire event began in " + initializer.getWorld().getName() + " at " + initializer.getBlockX() + ", "
				+ initializer.getBlockZ());
	}

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#stop()
	 */
	@Override
	public synchronized boolean stop() {
		return true;
	}

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#isStarted()
	 */
	@Override
	public boolean isStarted() {
		return true;
	}

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#canStart()
	 */
	@Override
	public boolean canStart() {
		return initializer != null;
	}

	/**
	 * Set the location this event should start at. Will search for the tree
	 * nearest this location.
	 * 
	 * @param location
	 *            The location.
	 */
	public void setInitializer(Location location) {
		initializer = location;
	}

	/**
	 * @return the initializer.
	 */
	public Location getInitializer() {
		return initializer;
	}

	/**
	 * Smite a given number of blocks of a given type in a given radius of a
	 * given location.
	 * 
	 * @param location
	 *            The location to center the search on.
	 * @param radius
	 *            The radius to search in.
	 * @param type
	 *            The material to search for.
	 * @param amount
	 *            The maximum number of blocks to smite.
	 */
	private void smiteNearbyBlocks(Location location, int radius, Material type, int amount) {
		Block start = location.getBlock();
		int count = 0;

		// Faces to combine for searching.
		BlockFace[] faces = { BlockFace.UP, BlockFace.NORTH, BlockFace.EAST };
		BlockFace[][] otherFaces = { { BlockFace.NORTH, BlockFace.EAST }, { BlockFace.UP, BlockFace.EAST },
				{ BlockFace.NORTH, BlockFace.UP } };
		for (int testRadius = 0; testRadius <= radius; testRadius++) {
			for (int testFace = 0; testFace < 6; testFace++) {
				// Check all the faces.
				BlockFace face = faces[testFace % 3];
				BlockFace[] other = otherFaces[testFace % 3];
				if (testFace >= 3) {
					face = face.getOppositeFace();
				}
				Block testBlock = start.getRelative(face, testRadius);
				// And the blocks with corresponding faces in the radius.
				for (int x = -testRadius; x <= testRadius; x++) {
					for (int y = -testRadius; y <= testRadius; y++) {
						Block nearBlock = testBlock.getRelative(other[0], x).getRelative(other[1], y);
						if (nearBlock.getTypeId() == type.getId() && nearBlock.getRelative(BlockFace.UP).getType() == Material.AIR) {
							TenJava.instance
									.getServer()
									.getScheduler()
									.runTaskLater(TenJava.instance, new SmiteInterrupt(nearBlock),
											RandomManager.getRandomDuration(count * 5L, count * 5L + 20L));
							amount--;
							count++;
							if (amount <= 0) { return; }
						}
					}
				}
			}
		}
	}

	private class SmiteInterrupt extends BukkitRunnable {

		private final Block block;

		public SmiteInterrupt(Block block) {
			this.block = block;
		}

		@Override
		public void run() {
			if (block.getRelative(BlockFace.UP).getType() == Material.AIR) {
				block.getWorld().strikeLightning(block.getLocation());
				block.getRelative(BlockFace.UP).setType(Material.FIRE);
			}
		}
	}
}
