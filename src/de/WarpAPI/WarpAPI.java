package de.WarpAPI;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WarpAPI extends JavaPlugin {

	private static WarpAPI instance;

	@Override
	public void onEnable() {
		instance = this;
		getDataFolder().mkdir();
	}

	public static void setWarp(Player player, String warpname) {
		if (!instance.getDataFolder().exists()) instance.getDataFolder().mkdir();
		File file = new File("plugins//WarpAPI//Warps//" + warpname.toLowerCase() + ".yml ");
		File folder = new File(file.getParent());
		if (!folder.exists()) folder.mkdir();
		if (!file.exists()) try {
			file.createNewFile();
		} catch (IOException e) {}
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		Location loc = player.getLocation();
		cfg.set("X", loc.getX());
		cfg.set("Y", loc.getY());
		cfg.set("Z", loc.getZ());
		cfg.set("Yaw", loc.getYaw());
		cfg.set("Pitch", loc.getPitch());
		cfg.set("WorldName", loc.getWorld().getName());
		try {
			cfg.save(file);
		} catch (IOException e) {}
	}

	public static void warp(Player player, String warpname) {
		File file = new File("plugins//WarpAPI//Warps//" + warpname.toLowerCase() + ".yml ");
		if (!file.exists()) return;
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		World world = Bukkit.getWorld(cfg.getString("WorldName"));
		double yaw = cfg.getDouble("Yaw");
		double pitch = cfg.getDouble("Pitch");
		Location loc = new Location(world, cfg.getDouble("X"), cfg.getDouble("Y"), cfg.getDouble("Z"), (float) yaw,
				(float) pitch);
		player.teleport(loc);
	}

	public static void delWarp(String warpname) {
		File file = new File("plugins//WarpAPI//Warps//" + warpname.toLowerCase() + ".yml ");
		if (file.exists()) file.delete();
	}

} // 76