/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3.events;

import java.util.UUID;

import main.java.com.tenjava.entries.JordanSicherman.t3.EventManager;
import main.java.com.tenjava.entries.JordanSicherman.t3.TenJava;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Jordan
 * 
 *         An apocalyptic event. Entities can become apocalypsed if they are
 *         humanoid. Once apocalypsed, entities shall become zombie villagers
 *         and infect other valid entities they deal damage to. Players,
 *         however, will simply
 */
public class ApocalypseEvent extends RandomEvent {

	private transient LivingEntity initializer;
	private transient World startWorld;
	private boolean isStarted;

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#start()
	 */
	@Override
	public void start() {
		if (!canStart()) {
			TenJava.log("An apocalypse event attempted to, and failed, to begin.");
			return;
		}

		EventManager.registerEvent(this);

		startWorld = initializer.getWorld();
		infect(initializer);

		TenJava.log("An apocalypse event began in " + startWorld.getName() + " with entity #" + initializer.getEntityId() + ".");
	}

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#stop()
	 */
	@Override
	public boolean stop() {
		TenJava.log("An apocalypse event ended.");
		EventManager.unregisterEvent(this);

		// Make sure we uninfect all the infectees from our starting world. May
		// cause problems if an infectee leaves the world.
		for (Entity entity : startWorld.getEntities()) {
			if (isInfected(entity)) {
				uninfect((LivingEntity) entity);
			}
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
		return initializer != null;
	}

	/**
	 * Set an entity that will initialize this apocalypse. Every humanoid entity
	 * this entity touches shall become a zombie villager.
	 * 
	 * @param entity
	 *            The entity.
	 */
	public void setInitializer(LivingEntity entity) {
		initializer = entity;
	}

	/**
	 * @return the initializer.
	 */
	public LivingEntity getInitializer() {
		return initializer;
	}

	/**
	 * Infect a given entity with apocalypse-ism.
	 * 
	 * @param entity
	 *            The entity.
	 */
	public static void infect(LivingEntity entity) {
		EntityType type = entity.getType();

		switch (type) {
		case CREEPER:
		case ZOMBIE:
		case ENDERMAN:
		case PIG_ZOMBIE:
		case VILLAGER:
		case WITCH:
		case SKELETON:
			// Spawn in a zombie and make it a zombie villager.
			Zombie zombie = (Zombie) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.ZOMBIE);
			zombie.setVillager(true);
			zombie.getEquipment().setArmorContents(entity.getEquipment().getArmorContents());
			zombie.setHealth(entity.getHealth());
			// Remove our old entity.
			entity.remove();
			entity = zombie;
		case PLAYER:
			// Give the entity a fixed meta value and a potion effect.
			entity.setMetadata("APOCALYPSE", new FixedMetadataValue(TenJava.instance, type));
			entity.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, Integer.MAX_VALUE, 0));
			break;
		default:
			break;
		}
	}

	/**
	 * @param entity
	 *            The entity in question.
	 * @return true if the entity is infected (has certain metadata).
	 */
	public static boolean isInfected(Entity entity) {
		return entity.hasMetadata("APOCALYPSE");
	}

	/**
	 * Uninfect a given entity and return it to its original state.
	 * 
	 * @param entity
	 *            The entity to uninfect.
	 */
	public static void uninfect(LivingEntity entity) {
		if (!isInfected(entity)) { return; }

		EntityType type = entity.getType();

		switch (type) {
		case ZOMBIE:
			// Spawn in an entity of the correct type.
			LivingEntity newentity = (LivingEntity) entity.getWorld().spawnEntity(entity.getLocation(),
					EntityType.valueOf(entity.getMetadata("APOCALYPSE").get(0).asString()));
			newentity.getEquipment().setArmorContents(entity.getEquipment().getArmorContents());
			newentity.setHealth(entity.getHealth());
			// Remove our zombie villager.
			entity.remove();
			entity = newentity;
		case PLAYER:
			// Remove our metadata.
			entity.removeMetadata("APOCALYPSE", TenJava.instance);
			entity.removePotionEffect(PotionEffectType.HUNGER);
			break;
		default:
			break;
		}
	}
}
