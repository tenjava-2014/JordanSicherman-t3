/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3;

import java.util.Random;

import main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent;

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
		TenJava.instance.getServer().getScheduler().runTaskLater(TenJava.instance, new RandomInterrupt(), timeUntilEvent());
	}

	/**
	 * @return a random number of ticks until an event can occur (between 5 and
	 *         20 minutes).
	 */
	private static long timeUntilEvent() {
		long min = 6000L; // 5 minutes.
		long max = 24000L; // 20 minutes.

		// Get a random tick number between 5 minutes and 20 minutes.
		return min + ((long) (random.nextDouble() * (max - min)));
	}

	public static void end() {
		isRunning = false;
	}

	private static class RandomInterrupt extends BukkitRunnable {

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			if (!isRunning) { return; }

			// Start a random event.
			EventManager.initializeEvent(RandomEvent.RandomEventType.getRandom());

			// Start a new random scheduler.
			begin();
		}
	}
}
