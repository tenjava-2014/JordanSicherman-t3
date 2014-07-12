/**
 * 
 */
package com.tenjava.entries.JordanSicherman.t3.events;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;

import com.tenjava.entries.JordanSicherman.t3.EventManager;
import com.tenjava.entries.JordanSicherman.t3.TenJava;

/**
 * @author Jordan
 * 
 *         A fashion show event. All entities capable of displaying armor, will.
 */
public class FashionShowEvent extends RandomEvent {

	private static Random random = new Random();
	private transient World startWorld;
	private boolean isStarted;

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#start()
	 */
	@Override
	public void start() {
		if (!canStart()) {
			TenJava.log("A fashion show event attempted to, and failed, to begin.");
			return;
		}

		EventManager.registerEvent(this);

		for (Entity entity : startWorld.getEntities()) {
			if (entity instanceof LivingEntity)
				fashion((LivingEntity) entity);
		}

		isStarted = true;
		TenJava.log("A fashion show event began in " + startWorld.getName() + ".");
		
		for (Player player : startWorld.getPlayers()) {
			player.sendMessage(ChatColor.LIGHT_PURPLE + "The mobs are putting on a show!");
		}
	}

	/* (non-Javadoc)
	 * @see main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent#stop()
	 */
	@Override
	public synchronized boolean stop() {
		if (!isStarted()) { return false; }

		isStarted = false;
		TenJava.log("A fashion show event ended.");
		EventManager.unregisterEvent(this);

		for (Entity entity : startWorld.getEntities()) {
			if (entity instanceof LivingEntity)
				unfashion((LivingEntity) entity);
		}
		
		for (Player player : startWorld.getPlayers()) {
			player.sendMessage(ChatColor.LIGHT_PURPLE + "Show time is over.");
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
	 * Set the world this event should start in.
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

	/**
	 * Whether or not an entity is a fashion show member.
	 * 
	 * @param entity
	 *            The entity.
	 * @return true if the entity is a fashion show member (has certain
	 *         metadata).
	 */
	public static boolean isFashionable(Entity entity) {
		return entity.hasMetadata("FASHIONABLE");
	}

	/**
	 * Make a certain entity very fashionable.
	 * 
	 * @param entity
	 *            The entity.
	 * @return false if unable to fashion the entity.
	 */
	public static boolean fashion(LivingEntity entity) {
		if (isFashionable(entity)) { return false; }

		switch (entity.getType()) {
		case SKELETON:
		case ZOMBIE:
		case PIG_ZOMBIE:
			break;
		default:
			return false;
		}
		entity.setMetadata("FASHIONABLE", new FixedMetadataValue(TenJava.instance, entity.getEquipment().getArmorContents()));

		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		colorArmor(helmet);
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		colorArmor(chestplate);
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		colorArmor(leggings);
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		colorArmor(boots);
		entity.getEquipment().setArmorContents(Arrays.asList(helmet, chestplate, leggings, boots).toArray(new ItemStack[0]));

		return true;
	}

	/**
	 * Color a piece of armor randomly.
	 * 
	 * @param armor
	 *            An ItemStack that must be of leather armor type.
	 */
	private static void colorArmor(ItemStack armor) {
		LeatherArmorMeta meta = (LeatherArmorMeta) armor.getItemMeta();
		meta.setColor(Color.fromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
		armor.setItemMeta(meta);
	}

	public static void unfashion(LivingEntity entity) {
		if (!isFashionable(entity)) { return; }
		entity.getEquipment().setArmorContents((ItemStack[]) entity.getMetadata("FASHIONABLE").get(0).value());
		entity.removeMetadata("FASHIONABLE", TenJava.instance);
	}
}
