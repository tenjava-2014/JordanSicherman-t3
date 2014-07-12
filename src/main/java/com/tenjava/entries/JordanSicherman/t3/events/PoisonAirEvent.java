/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3.events;

import main.java.com.tenjava.entries.JordanSicherman.t3.EventManager;
import main.java.com.tenjava.entries.JordanSicherman.t3.RandomManager;
import main.java.com.tenjava.entries.JordanSicherman.t3.TenJava;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Jordan
 * 
 *         A poison air event. When started, all oxygen becomes fatal and
 *         players exposed begin to take damage. They must be in a liquid to be
 *         safe.
 */
public class PoisonAirEvent extends RandomEvent {

	private transient World startWorld;
	private BukkitTask task = null;
	private boolean isStarted;

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#start()
	 */
	@Override
	public void start() {
		if (!canStart()) {
			TenJava.log("A poisoned air event attempted to, and failed, to begin.");
			return;
		}

		isStarted = true;
		EventManager.registerEvent(this);

		for (Player player : startWorld.getPlayers()) {
			player.sendMessage(ChatColor.BLUE + "Smog rolls in and you feel your throat constrict. Get underwater!");
		}

		// Start some tasks to make poisoned air damage the player every so
		// often and cancel the task eventually.
		TenJava.instance.getServer().getScheduler().runTaskTimer(TenJava.instance, new PoisonAirInterrupt(), 60L, 60L);
		long duration = RandomManager.getRandomDuration(1200L, 4800L);
		TenJava.instance.getServer().getScheduler().runTaskLater(TenJava.instance, new Runnable() {
			@Override
			public void run() {
				stop();
			}
		}, duration);

		TenJava.log("A poisoned air event began in " + startWorld.getName() + " and will clear in " + (duration / 20 / 60) + " minutes.");
	}

	private class PoisonAirInterrupt extends BukkitRunnable {

		@Override
		public void run() {
			for (Player player : startWorld.getPlayers()) {
				if (!player.getEyeLocation().getBlock().isLiquid()) {
					player.damage(1.0);
					player.setLastDamageCause(new EntityDamageEvent(player, DamageCause.POISON, 1.0));
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

		TenJava.log("A poisoned air event ended.");
		EventManager.unregisterEvent(this);

		// Cancel the damaging task.
		task.cancel();
		for (Player player : startWorld.getPlayers()) {
			player.sendMessage(ChatColor.BLUE + "The smog has cleared.");
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
