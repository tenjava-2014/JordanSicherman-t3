/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3.events;

import java.util.UUID;

import main.java.com.tenjava.entries.JordanSicherman.t3.EventManager;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * @author Jordan
 * 
 */
public class ApocalypseEvent extends RandomEvent {

	private transient Entity initializer;
	private boolean isStarted;

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#start()
	 */
	@Override
	public void start() {
		if (!canStart()) { return; }

		EventManager.registerEvent(this);
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#stop()
	 */
	@Override
	public void stop() {
		EventManager.unregisterEvent(this);
		// TODO Auto-generated method stub

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
		return initializer != null;
	}

	/**
	 * Set an entity that will initialize this apocalypse. Every humanoid entity
	 * this entity touches shall become a zombie villager.
	 * 
	 * @param entity
	 *            The entity.
	 */
	public void setInitializer(Entity entity) {
		initializer = entity;
	}
}
