/**
 * 
 */
package main.java.com.tenjava.entries.JordanSicherman.t3.events;

import main.java.com.tenjava.entries.JordanSicherman.t3.RandomManager;
import main.java.com.tenjava.entries.JordanSicherman.t3.TenJava;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Jordan
 * 
 *         A unique event in the sense that it is always active. If a food item
 *         is in a player's inventory, it has a chance to become rotten flesh
 *         eventually.
 */
public class FoodRotEvent {

	private static BukkitTask task;

	public static void start() {
		task = TenJava.instance.getServer().getScheduler().runTaskTimer(TenJava.instance, new FoodRotInterrupt(), 60L, 100L);
	}

	public static void stop() {
		task.cancel();
	}

	private static class FoodRotInterrupt extends BukkitRunnable {
		@Override
		public void run() {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (RandomManager.getRandomDuration(0, 400) <= 1) {
					for (ItemStack item : player.getInventory().getContents()) {
						// It's food!
						if (isFood(item.getType()) && RandomManager.getRandomDuration(0, 36) == 1L) {
							// More than one item...
							if (item.getAmount() > 1) {
								item.setAmount(item.getAmount() - 1);

								// Space in inventory.
								if (player.getInventory().firstEmpty() >= 0) {
									player.getInventory().addItem(new ItemStack(Material.ROTTEN_FLESH));
									break;
								}

								player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.ROTTEN_FLESH));
								break;
							}

							// Turn it into flesh.
							item.setType(Material.ROTTEN_FLESH);
						}
					}
				}
			}
		}
	}

	/**
	 * @param m
	 *            A material in question.
	 * @return true if the material is a foodstuff.
	 */
	private static boolean isFood(Material m) {
		switch (m) {
		case APPLE:
		case BREAD:
		case PORK:
		case GRILLED_PORK:
		case RAW_FISH:
		case COOKED_FISH:
		case CAKE:
		case COOKIE:
		case MELON:
		case RAW_BEEF:
		case COOKED_BEEF:
		case RAW_CHICKEN:
		case COOKED_CHICKEN:
		case CARROT_ITEM:
		case POTATO_ITEM:
		case BAKED_POTATO:
		case PUMPKIN_PIE:
			return true;
		default:
			return false;
		}
	}
}
