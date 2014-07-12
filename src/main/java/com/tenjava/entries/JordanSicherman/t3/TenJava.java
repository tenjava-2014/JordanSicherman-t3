/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3;

import main.java.com.tenjava.entries.JordanSicherman.t3.commands.EventCommand;
import main.java.com.tenjava.entries.JordanSicherman.t3.events.FoodRotEvent;
import main.java.com.tenjava.entries.JordanSicherman.t3.listeners.ApocalypseListener;
import main.java.com.tenjava.entries.JordanSicherman.t3.listeners.NoTreeListener;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Jordan
 * 
 */
public class TenJava extends JavaPlugin {

	public static TenJava instance; // :)

	public static void log(String message) {
		instance.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + message);
	}

	/**
	 * Do things when our plugin is enabled.
	 */
	public void onEnable() {
		// Store an instance variable.
		instance = this;
		
		PluginManager pm = getServer().getPluginManager();

		// Make sure our config file exists.
		saveDefaultConfig();

		if("SECONDARY".equalsIgnoreCase(getConfig().getString("mode"))) {
			pm.registerEvents(new NoTreeListener(), this);
			return;
		}
		// Register our command executors.
		getCommand("event").setExecutor(new EventCommand());

		// Register our events.
		pm.registerEvents(new ApocalypseListener(), this);

		// Start our random manager to allow events to begin occuring.
		RandomManager.begin();
		
		// Start food rotting.
		FoodRotEvent.start();
	}

	/**
	 * Do things when our plugin is disabled.
	 */
	public void onDisable() {
		reloadConfig();

		// Stop our random manager.
		RandomManager.end();

		// Cancel all our server events.
		EventManager.stopAllEvents();
		FoodRotEvent.stop();

		getServer().getScheduler().cancelTasks(this);
	}
}
