/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3.commands;

import main.java.com.tenjava.entries.JordanSicherman.t3.EventManager;
import main.java.com.tenjava.entries.JordanSicherman.t3.TenJava;
import main.java.com.tenjava.entries.JordanSicherman.t3.events.RandomEvent.RandomEventType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 */
public class EventCommand implements CommandExecutor {

	/* (non-Javadoc)
	 * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Command can appear as one of the following:
		// /event begin <NAME> <PLAYER>
		// /event begin <NAME>
		// /event end <NAME>
		// /event end

		// Nothing was passed.
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please specify what you would like to do. Valid arguments are: " + ChatColor.YELLOW
					+ "begin, end");
			return true;
		}

		// End was passed.
		if (args[0].equalsIgnoreCase("end")) {
			if (args.length == 1) {
				int eventsStopped = EventManager.stopAllEvents();
				sender.sendMessage(ChatColor.YELLOW + "All events " + ChatColor.GOLD + "(" + eventsStopped + ")" + ChatColor.YELLOW
						+ " have been ended.");
				return true;
			}
			RandomEventType type = null;
			try {
				type = RandomEventType.valueOf(args[1].toUpperCase());
			} catch (Exception exc) {
			}
			if (type == null) {
				sender.sendMessage(ChatColor.RED + "Please specify a valid event type. Valid arguments are: " + ChatColor.YELLOW
						+ RandomEventType.getFormattedListOfEvents());
				return true;
			}
			if (!EventManager.isInEffect(type)) {
				sender.sendMessage(ChatColor.RED + "That event isn't currently executing.");
				return true;
			}
			int eventsStopped = EventManager.stopAllEvents(type);
			sender.sendMessage(ChatColor.YELLOW + "All " + type.toString().toLowerCase() + " events " + ChatColor.GOLD + "("
					+ eventsStopped + ")" + ChatColor.YELLOW + " have been ended.");
			return true;
		}

		// Begin was passed.
		if (args[0].equalsIgnoreCase("begin")) {
			if (args.length == 1) {
				sender.sendMessage(ChatColor.RED
						+ "Please specify an event type to begin and (optionally) a player to start the event near. Valid arguments are: "
						+ ChatColor.YELLOW + RandomEventType.getFormattedListOfEvents());
				return true;
			}

			RandomEventType type = null;
			try {
				type = RandomEventType.valueOf(args[1].toUpperCase());
			} catch (Exception exc) {
			}
			if (type == null) {
				sender.sendMessage(ChatColor.RED + "Please specify a valid event type. Valid arguments are: " + ChatColor.YELLOW
						+ RandomEventType.getFormattedListOfEvents());
				return true;
			}

			Player player = null;
			if (args.length == 3) {
				player = TenJava.instance.getServer().getPlayer(args[2]);
			}

			EventManager.initializeEvent(type, player == null ? null : player.getLocation());
			sender.sendMessage(ChatColor.YELLOW + "The event '" + type.toString().toLowerCase() + "' has been started"
					+ (player == null ? "." : ("near " + player.getName() + ".")));
			return true;
		}

		// Something else was passed.
		sender.sendMessage(ChatColor.RED + "Please specify what you would like to do. Valid arguments are: " + ChatColor.YELLOW
				+ "begin, end");
		return true;
	}
}