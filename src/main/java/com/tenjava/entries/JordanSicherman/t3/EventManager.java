/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;

import main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent;
import main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent.RandomEventType;

/**
 * @author Jordan
 * 
 */
public class EventManager {

	// All events on the server.
	private static List<RandomEvent> serverEvents = new ArrayList<RandomEvent>();

	/**
	 * Register an event.
	 * 
	 * @param event
	 *            The event.
	 */
	public static void registerEvent(RandomEvent event) {
		serverEvents.add(event);
	}

	/**
	 * Unregister an event.
	 * 
	 * @param event
	 *            The event.
	 * @return true if the event could be unregistered.
	 */
	public static boolean unregisterEvent(RandomEvent event) {
		return serverEvents.remove(event);
	}

	/**
	 * @see EventManager#stopAllEvents(RandomEventType... type).
	 */
	public static int stopAllEvents() {
		return stopAllEvents(RandomEventType.values());
	}

	/**
	 * Forcibly cancel all events.
	 * 
	 * @param type
	 *            The RandomEventType(s) to stop.
	 * @return the number of events stopped.
	 */
	public static int stopAllEvents(RandomEventType... type) {
		int count = 0;
		List<RandomEventType> types = Arrays.asList(type);

		// Iterate and stop everything.
		for (RandomEvent event : serverEvents) {
			for (RandomEventType randomevent : types) {
				if (event.getClass().equals(randomevent.getUnderlyingClass())) {
					if (event.stop()) {
						count++;
					}
					continue;
				}
			}
		}
		return count;
	}

	/**
	 * Whether or not a certain event is currently executing.
	 * 
	 * @param type
	 *            The event type.
	 * @return true if the event is in place.
	 */
	public static boolean isInEffect(RandomEventType type) {
		// Compare the class of every current event to that of the one in
		// question.
		for (RandomEvent event : serverEvents) {
			// If the classes are equal, we have our match!
			if (event.getClass().equals(type.getUnderlyingClass())) { return true; }
		}
		return false;
	}

	/**
	 * Register all the variables and begin an event for a specific
	 * RandomEventType.
	 * 
	 * @param type
	 *            The RandomEventType to begin.
	 * @param proximity
	 *            A location near where the event should start (or null if not
	 *            required).
	 */
	public static void initializeEvent(RandomEventType type, Location proximity) {
		// TODO
		switch (type) {
		case APOCALYPSE:
			break;
		default:
			break;
		}
	}
}
