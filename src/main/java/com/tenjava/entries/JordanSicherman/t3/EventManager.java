/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3;

import java.util.ArrayList;
import java.util.List;

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
	 * Forcibly cancel all events.
	 */
	public static void stopAllEvents() {
		// Iterate and stop everything.
		for (RandomEvent event : serverEvents) {
			event.stop();
		}
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
	 */
	public static void initializeEvent(RandomEventType type) {
		// TODO Auto-generated method stub

	}
}
