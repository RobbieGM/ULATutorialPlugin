package com.unitedlearningacademy.TutorialPlugin.wands;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.unitedlearningacademy.TutorialPlugin.Main;

public class MyFirstWand extends Wand {

	public MyFirstWand(Main plugin) {
		super(plugin);
	}

	@Override
	public boolean use(ItemStack item, Player player, World world, Server server) {
		// Put the main code for your wand here.
		server.broadcastMessage("My First Wand was used!");
		return true;
	}

	@Override
	public long getPlayerCooldown() {
		return 500;
	}

	@Override
	public String getName() {
		return "My First Wand";
	}

	@Override
	public String getLore() {
		return "Put a description of what your wand does here.";
	}
	
	public Material getWandTip() {
		return Material.ACACIA_BOAT;
	}

}
