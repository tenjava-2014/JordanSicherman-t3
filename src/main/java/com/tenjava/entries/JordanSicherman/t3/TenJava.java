/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3;

import main.java.com.tenjava.entries.JordanSicherman.t3.commands.EventCommand;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Jordan
 * 
 */
public class TenJava extends JavaPlugin {

	public static TenJava instance;

	public static void log(String message) {
		instance.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + message);
	}

	/**
	 * Do things when our plugin is enabled.
	 */
	public void onEnable() {
		// Store an instance variable.
		instance = this;

		// Make sure our config file exists.
		saveDefaultConfig();

		// Register our command executors.
		getCommand("event").setExecutor(new EventCommand());

		// Register our events.
		PluginManager pm = getServer().getPluginManager();

		// Start our random manager to allow events to begin occuring.
		RandomManager.begin();
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

		getServer().getScheduler().cancelTasks(this);
	}
}
