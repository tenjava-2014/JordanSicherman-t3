/**
 * 
 */
package com.tenjava.entries.JordanSicherman.t3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.tenjava.entries.JordanSicherman.t3.events.ApocalypseEvent;
import com.tenjava.entries.JordanSicherman.t3.events.FashionShowEvent;
import com.tenjava.entries.JordanSicherman.t3.events.ForestFireEvent;
import com.tenjava.entries.JordanSicherman.t3.events.MushroomRainEvent;
import com.tenjava.entries.JordanSicherman.t3.events.PiranhasEvent;
import com.tenjava.entries.JordanSicherman.t3.events.PoisonAirEvent;
import com.tenjava.entries.JordanSicherman.t3.events.RandomEvent;
import com.tenjava.entries.JordanSicherman.t3.events.RandomEvent.RandomEventType;

/**
 * @author Jordan
 * 
 */
public class EventManager {

	private static Random random = new Random();

	// All events on the server.
	private static Set<RandomEvent> serverEvents = new HashSet<RandomEvent>();

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
	public synchronized static boolean unregisterEvent(RandomEvent event) {
		return serverEvents.remove(event);
	}

	/**
	 * @see EventManager#stopAllEvents(RandomEventType... type).
	 */
	public synchronized static int stopAllEvents() {
		return stopAllEvents(RandomEventType.values());
	}

	/**
	 * Forcibly cancel all events.
	 * 
	 * @param type
	 *            The RandomEventType(s) to stop.
	 * @return the number of events stopped.
	 */
	public synchronized static int stopAllEvents(RandomEventType... type) {
		int count = 0;
		List<RandomEventType> types = Arrays.asList(type);

		// Iterate and stop everything.
		for (RandomEvent event : new HashSet<RandomEvent>(serverEvents)) {
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
		for (RandomEvent event : new HashSet<RandomEvent>(serverEvents)) {
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
	 * @return false if proximity was required and not provided.
	 */
	public static boolean initializeEvent(RandomEventType type, Location proximity) {
		switch (type) {
		case APOCALYPSE:
			if (proximity == null) { return false; }
			List<LivingEntity> entities = getNearbyEntities(proximity, 10);
			ApocalypseEvent apocEvent = new ApocalypseEvent();
			apocEvent.setInitializer(entities.get(random.nextInt(entities.size())));
			apocEvent.start();
			break;
		case SMOG:
			PoisonAirEvent smogEvent = new PoisonAirEvent();
			smogEvent.setInitializer(proximity == null ? TenJava.instance.getServer().getWorlds().get(0) : proximity.getWorld());
			smogEvent.start();
			break;
		case PIRANHAS:
			PiranhasEvent piranhasEvent = new PiranhasEvent();
			piranhasEvent.setInitializer(proximity == null ? TenJava.instance.getServer().getWorlds().get(0) : proximity.getWorld());
			piranhasEvent.start();
			break;
		case FOREST_FIRE:
			if (proximity == null) { return false; }
			ForestFireEvent forestEvent = new ForestFireEvent();
			forestEvent.setInitializer(proximity);
			forestEvent.start();
			break;
		case MUSHROOM_RAIN:
			MushroomRainEvent shroomEvent = new MushroomRainEvent();
			shroomEvent.setInitializer(proximity == null ? TenJava.instance.getServer().getWorlds().get(0) : proximity.getWorld());
			shroomEvent.start();
			break;
		case FASHION_SHOW:
			FashionShowEvent fashionEvent = new FashionShowEvent();
			fashionEvent.setInitializer(proximity == null ? TenJava.instance.getServer().getWorlds().get(0) : proximity.getWorld());
			fashionEvent.start();
			break;
		default:
			break;
		}

		return true;
	}

	/**
	 * Get humanoid entities nearby a location.
	 * 
	 * @param location
	 *            The location.
	 * @param radius
	 *            The radius to check.
	 * @return The HashSet of humanoid entities near the location.
	 */
	private static List<LivingEntity> getNearbyEntities(Location location, int radius) {
		int radiusSquared = radius * radius;
		int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		List<LivingEntity> radiusEntities = new ArrayList<LivingEntity>();
		for (int chunkX = -chunkRadius; chunkX <= chunkRadius; chunkX++) {
			for (int chunkZ = -chunkRadius; chunkZ <= chunkRadius; chunkZ++) {
				int x = (int) location.getX();
				int y = (int) location.getY();
				int z = (int) location.getZ();
				Location inLocation = new Location(location.getWorld(), x + (chunkX * 16), y, z + (chunkZ * 16));
				for (Entity entity : inLocation.getChunk().getEntities()) {
					// Only use humanoid entities.
					switch (entity.getType()) {
					case CREEPER:
					case ZOMBIE:
					case ENDERMAN:
					case PIG_ZOMBIE:
					case VILLAGER:
					case WITCH:
					case SKELETON:
					case PLAYER:
						break;
					default:
						continue;
					}

					if (entity.getLocation().distanceSquared(location) <= radiusSquared
							&& entity.getLocation().getBlock() != location.getBlock())
						radiusEntities.add((LivingEntity) entity);
				}
			}
		}
		return radiusEntities;
	}
}
