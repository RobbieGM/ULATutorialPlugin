package com.unitedlearningacademy.TutorialPlugin;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class UseableItem {
	protected Main plugin;

	public UseableItem(Main plugin) {
		this.plugin = plugin;
	}

	/**
	 * 
	 * @param item   The item in hand
	 * @param player The player using the item
	 * @param world  The world the item was used in
	 * @param server The server
	 * @return Whether or not the use of the item was successful. If the item was
	 *         not successfully used no cooldown will be applied.
	 */
	abstract public boolean use(ItemStack item, Player player, World world, Server server);

	/**
	 * The cooldown that will be applied to the player, in milliseconds. Player
	 * cooldowns affect every item.
	 */
	abstract public long getPlayerCooldown();
	
	/**
	 * The item name.
	 */
	public abstract String getName();

	/**
	 * The cooldown that will be applied to the item. Item cooldowns only affect
	 * individual items and other items can be used immediately.
	 */
	public long getItemCooldown() {
		return getPlayerCooldown();
	}
	/**
	 * The item description.
	 */
	abstract public String getLore();
}
