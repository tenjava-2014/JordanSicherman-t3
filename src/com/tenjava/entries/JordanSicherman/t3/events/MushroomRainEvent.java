/**
 * 
 */
package com.tenjava.entries.JordanSicherman.t3.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.tenjava.entries.JordanSicherman.t3.EventManager;
import com.tenjava.entries.JordanSicherman.t3.RandomManager;
import com.tenjava.entries.JordanSicherman.t3.TenJava;

/**
 * @author Jordan
 * 
 *         A mushroom rain event. Will rain mushrooms above all players, just
 *         for fun.
 */
public class MushroomRainEvent extends RandomEvent {

	private transient World startWorld;
	private BukkitTask task = null;
	private boolean isStarted;

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#start()
	 */
	@Override
	public void start() {
		if (!canStart()) {
			TenJava.log("A mushroom rain event attempted to, and failed, to begin.");
			return;
		}

		isStarted = true;
		EventManager.registerEvent(this);

		for (Player player : startWorld.getPlayers()) {
			player.sendMessage(ChatColor.DARK_GRAY + "You see something fall from the sky...");
		}

		// Start some tasks to make mushrooms rain to the player every so
		// often and cancel the task eventually.
		task = TenJava.instance.getServer().getScheduler().runTaskTimer(TenJava.instance, new MushroomInterrupt(), 60L, 2L);
		long duration = RandomManager.getRandomInRange(1200L, 4800L);
		TenJava.instance.getServer().getScheduler().runTaskLater(TenJava.instance, new Runnable() {
			@Override
			public void run() {
				stop();
			}
		}, duration);

		TenJava.log("A mushroom rain event began in " + startWorld.getName() + " and will clear in " + (duration / 20 / 60) + " minutes.");
	}

	private class MushroomInterrupt extends BukkitRunnable {

		@Override
		public void run() {
			for (Player player : startWorld.getPlayers()) {
				if (!player.isDead()) {
					// Drop random mushrooms near each player.
					Location location = player
							.getLocation()
							.clone()
							.add(RandomManager.getRandomInRange(0, 10) * (RandomManager.comparator() ? -1 : 1), 20,
									RandomManager.getRandomInRange(0, 10) * (RandomManager.comparator() ? -1 : 1));
					location.getWorld().dropItemNaturally(location,
							new ItemStack(RandomManager.comparator() ? Material.RED_MUSHROOM : Material.BROWN_MUSHROOM));
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#stop()
	 */
	@Override
	public synchronized boolean stop() {
		if (!isStarted()) { return false; }

		TenJava.log("A mushroom rain event ended.");
		EventManager.unregisterEvent(this);

		// Cancel the damaging task.
		task.cancel();
		for (Player player : startWorld.getPlayers()) {
			player.sendMessage(ChatColor.DARK_GRAY + "The clouds part and the shroomy rain ceases.");
		}

		return true;
	}

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#isStarted()
	 */
	@Override
	public boolean isStarted() {
		return isStarted;
	}

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#canStart()
	 */
	@Override
	public boolean canStart() {
		return startWorld != null;
	}

	/**
	 * Set the world this event should take place on.
	 * 
	 * @param world
	 *            The world.
	 */
	public void setInitializer(World world) {
		startWorld = world;
	}

	/**
	 * @return the initializer.
	 */
	public World getInitializer() {
		return startWorld;
	}
}
