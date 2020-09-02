package com.unitedlearningacademy.TutorialPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.unitedlearningacademy.TutorialPlugin.wands.MyFirstWand;
import com.unitedlearningacademy.TutorialPlugin.wands.Wand;

public class ModdedItemManager implements Listener {

	public PlayerCooldownManager cooldownManager;
	List<Wand> wands = new ArrayList<>();
	Main plugin;
	Glow glow;

	public ModdedItemManager(Main plugin) {
		cooldownManager = new PlayerCooldownManager(plugin);
		this.plugin = plugin;
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			glow = new Glow(new NamespacedKey(plugin, "glow"));
			Enchantment.registerEnchantment(glow);
		} catch (Exception e) {
			plugin.getLogger().info(
					"Registering enchantment threw error because the enchantment was already registered (this is probably fine)");
		}

	}

	<T> List<T> createInstancesOfClasses(List<Class<? extends T>> classes) {
		return classes.stream().map(clazz -> {
			try {
				return clazz.getConstructor(Main.class).newInstance(plugin);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}

	void initItems() {
		List<Class<? extends Wand>> wandClasses = Arrays.asList(MyFirstWand.class);
		wands = createInstancesOfClasses(wandClasses);
	}

	public void onEnable() {
		initItems();

		wands.forEach((Wand wand) -> {
			ShapedRecipe wandRecipe = createRecipeFromResult(Material.STICK, wand.getName(), wand.getLore());
			wandRecipe.shape("  t", " s ", "b  ").setIngredient('t', wand.getWandTip()).setIngredient('s', Material.STICK)
					.setIngredient('b', Material.ENDER_PEARL);
			plugin.getServer().addRecipe(wandRecipe);
			
			// Disabled event handling due to complication for new programmers
//			if (wand.isEventHandler() && wand instanceof Listener) {
//				pm.registerEvents((Listener) wand, plugin);
//			}
		});
	}

	public void onDisable() {
		wands.clear();
	}

	ShapedRecipe createRecipeFromResult(Material itemMaterial, String name, String lore, int count) {
		ItemStack item = new ItemStack(itemMaterial, count);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore.split("\n")));
		meta.addEnchant(glow, 1, true);
		item.setItemMeta(meta);
		String keyName = name.toLowerCase().replaceAll("[^a-z0-9/._-]", "");
		return new ShapedRecipe(new NamespacedKey(plugin, keyName), item);
	}

	ShapedRecipe createRecipeFromResult(Material itemMaterial, String name, String lore) {
		return createRecipeFromResult(itemMaterial, name, lore, 1);
	}

	public <T extends UseableItem> T getUseableItem(List<T> items, String name) {
		Optional<T> optional = items.stream().filter(item -> item.getName().equals(name)).findFirst();
		return optional.isPresent() ? optional.get() : null;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		ItemStack item = e.getItem();
		Player player = e.getPlayer();
		boolean isRightClick = e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK;
		boolean isOtherInteraction = e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& e.getClickedBlock().getType().isInteractable()
				&& !e.getClickedBlock().getType().toString().contains("STAIRS");
		boolean isMainHand = e.getHand() == EquipmentSlot.HAND;
		if (item != null && !isOtherInteraction && isMainHand) {
			String itemName = item.getItemMeta().getDisplayName();
			boolean isStick = item.getType() == Material.STICK;

			Wand wand = getUseableItem(wands, itemName);
			if (wand != null && isStick && isRightClick) {
				if (cooldownManager.playerMayUseItem(player, wand)) {
					useWand(wand, item, player, false);
				}
			}
		}
	}

	void useWand(Wand wand, ItemStack item, Player player, boolean useAlt) {
		boolean wandUsed = wand.use(item, player, player.getWorld(), plugin.getServer());
		if (wandUsed) {
			cooldownManager.useItem(player, wand);
		}
	}
}
