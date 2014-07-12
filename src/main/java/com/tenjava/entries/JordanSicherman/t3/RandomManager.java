/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3;

import java.util.List;
import java.util.Random;

import main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 */
public class RandomManager {

	private static Random random = new Random();
	private static boolean isRunning = false;

	public static void begin() {
		isRunning = true;
		// Get a random time between 5 and 20 minutes.
		long time = getRandomDuration(6000L, 24000L);
		TenJava.log("A new event will start in " + (time / 20 / 60) + " minutes.");
		TenJava.instance.getServer().getScheduler().runTaskLater(TenJava.instance, new RandomInterrupt(), time);
	}

	/**
	 * @return a random long between @param min and @param max.
	 */
	public static long getRandomDuration(long min, long max) {
		// Get a random tick number between 5 minutes and 20 minutes.
		return min + ((long) (random.nextDouble() * (max - min)));
	}

	public static void end() {
		isRunning = false;
	}

	private static class RandomInterrupt extends BukkitRunnable {

		private final World world;

		public RandomInterrupt() {
			world = TenJava.instance.getServer().getWorlds().get(0);
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			List<Player> players = world.getPlayers();
			if (!isRunning || players.isEmpty()) { return; }

			// Start a random event.
			EventManager
					.initializeEvent(RandomEvent.RandomEventType.getRandom(), players.get(random.nextInt(players.size())).getLocation());

			// Start a new random scheduler.
			begin();
		}
	}

	/**
	 * @return either true or false.
	 */
	public static boolean comparator() {
		return random.nextBoolean();
	}
}
