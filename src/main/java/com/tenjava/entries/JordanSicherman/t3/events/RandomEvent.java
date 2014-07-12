/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Jordan
 * 
 */
public abstract class RandomEvent {

	public static enum RandomEventType {
		APOCALYPSE(ApocalypseEvent.class);

		// Define important variables for retrieving a random value in this
		// enum.
		private static final List<RandomEventType> randomEvents = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int size = randomEvents.size();
		private static final Random random = new Random();

		// The class this type represents.
		private Class<? extends RandomEvent> underlying;

		private RandomEventType(Class<? extends RandomEvent> clazz) {
			underlying = clazz;
		}

		public Class<? extends RandomEvent> getUnderlyingClass() {
			return underlying;
		}

		public static RandomEventType getRandom() {
			return randomEvents.get(random.nextInt(size));
		}
	}

	/**
	 * Start this event. Will fail silently if RandomEvent#canStart() resolves
	 * to false.
	 */
	public abstract void start();

	/**
	 * Forcibly stop this event.
	 */
	public abstract void stop();

	/**
	 * @return true if this event is started.
	 */
	public abstract boolean isStarted();

	/**
	 * @return true if this event can be started.
	 */
	public abstract boolean canStart();
}
