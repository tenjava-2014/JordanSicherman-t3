/**
 * 
 */
package com.tenjava.entries.JordanSicherman.t3.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.tenjava.entries.JordanSicherman.t3.EventManager;
import com.tenjava.entries.JordanSicherman.t3.events.ApocalypseEvent;
import com.tenjava.entries.JordanSicherman.t3.events.RandomEvent.RandomEventType;

/**
 * @author Jordan
 * 
 */
public class ApocalypseListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onDamageEntity(EntityDamageByEntityEvent e) {
		if (!EventManager.isInEffect(RandomEventType.APOCALYPSE)) { return; }

		Entity damager = e.getDamager();
		Entity damaged = e.getEntity();

		// Infect other entities if the damager is infected.
		if (damaged instanceof LivingEntity && ApocalypseEvent.isInfected(damager) && !ApocalypseEvent.isInfected(damaged)) {
			ApocalypseEvent.infect((LivingEntity) damaged);
		}
	}

	@EventHandler
	private void onDeath(PlayerDeathEvent e) {
		if (!EventManager.isInEffect(RandomEventType.APOCALYPSE)) { return; }

		if (ApocalypseEvent.isInfected(e.getEntity())) {
			ApocalypseEvent.uninfect(e.getEntity());
		}
	}
}
