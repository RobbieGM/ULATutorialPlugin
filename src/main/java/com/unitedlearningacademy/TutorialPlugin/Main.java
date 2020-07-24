package com.unitedlearningacademy.TutorialPlugin;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public ModdedItemManager moddedItemManager;

	public Main() {
		moddedItemManager = new ModdedItemManager(this);
	}
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PhantomSpawnPreventor(), this);
		pm.registerEvents(moddedItemManager, this);
		moddedItemManager.onEnable();
	}
	
    @Override
    public void onDisable() {
    	getServer().resetRecipes();
        moddedItemManager.onDisable();
    }
}
