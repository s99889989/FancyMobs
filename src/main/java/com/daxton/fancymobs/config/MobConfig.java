package com.daxton.fancymobs.config;

import com.daxton.fancymobs.FancyMobs;
import com.daxton.fancymobs.manager.MobManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MobConfig {

	public static void execute(){

		FileConfiguration mobConfig = FileConfig.config_Map.get("Mobs/DefaultMobs.yml");
		FileConfiguration mobDefaultConfig = FileConfig.config_Map.get("Default_Mob.yml");

		Arrays.stream(EntityType.values()).forEach(entityType -> {

			World world = Bukkit.getWorld("world");
			Location location = new Location(world, 0,0,0);
			if(world != null){
				try {
					String mobName = "Ender_Dragon";
					if(entityType != EntityType.ENDER_DRAGON){
						Entity entity = world.spawnEntity(location, entityType);
						LivingEntity livingEntity = (LivingEntity) entity;
						mobName = livingEntity.getName();
						livingEntity.remove();
					}

					mobConfig.set(mobName+".Type", mobName);
					mobConfig.set(mobName+".Display", mobName);
					Map<String, String> stats_Map = new ConcurrentHashMap<>();
					for(String key : mobDefaultConfig.getConfigurationSection("Default_Mob").getKeys(false)){
						String value = mobDefaultConfig.getString("Default_Mob."+key);
						if(!mobConfig.contains(mobName+".Custom."+key)){
							mobConfig.set(mobName+".Custom."+key, value);
						}else {
							value = mobConfig.getString(mobName+".Custom."+key);
						}
						stats_Map.put(key, value);
					}
					MobManager.mob_Stats_Map.put(mobName, stats_Map);

				}catch (ClassCastException | IllegalArgumentException exception){
					//exception.printStackTrace();
				}
				File file = new File(FancyMobs.fancyMobs.getDataFolder(), "Mobs/DefaultMobs.yml");
				try {
					mobConfig.save(file);
				}catch (IOException exception){
					//
				}

			}
		});

	}


}
