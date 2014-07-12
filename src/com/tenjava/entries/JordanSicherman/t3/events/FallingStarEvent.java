/**
 * 
 */
package com.tenjava.entries.JordanSicherman.t3.events;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import com.tenjava.entries.JordanSicherman.t3.RandomManager;
import com.tenjava.entries.JordanSicherman.t3.TenJava;

/**
 * @author Jordan
 * 
 *         A falling star event. A ghast fireball lands near a player and turns
 *         into ore!
 */
public class FallingStarEvent extends RandomEvent {

	private transient Location initializer;

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#start()
	 */
	@Override
	public void start() {
		if (!canStart()) {
			TenJava.log("A falling star event attempted to, and failed, to begin.");
			return;
		}

		Location start = initializer.clone().add(RandomManager.getRandomInRange(0, 30) * (RandomManager.comparator() ? -1 : 1), 60,
				RandomManager.getRandomInRange(0, 30) * (RandomManager.comparator() ? -1 : 1));
		Location end = initializer.clone().add(RandomManager.getRandomInRange(9, 15) * (RandomManager.comparator() ? -1 : 1), 0,
				RandomManager.getRandomInRange(9, 15) * (RandomManager.comparator() ? -1 : 1));

		// Steer the fireball...
		Fireball fireball = (Fireball) initializer.getWorld().spawnEntity(start, EntityType.FIREBALL);
		Vector from = start.toVector();
		Vector to = end.toVector();
		fireball.setDirection(to.subtract(from));
		fireball.setVelocity(to.subtract(from).normalize().multiply(0.05d));

		// Some properties...
		fireball.setIsIncendiary(true);
		fireball.setBounce(false);
		fireball.setYield(10f);
		fireball.setMetadata("FALLING_STAR", new FixedMetadataValue(TenJava.instance, true));

		TenJava.log("A falling star event began in " + initializer.getWorld().getName() + " at " + initializer.getBlockX() + ", "
				+ initializer.getBlockY() + ", " + initializer.getBlockZ());
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
}
