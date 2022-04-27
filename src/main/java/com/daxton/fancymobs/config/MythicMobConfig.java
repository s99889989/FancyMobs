package com.daxton.fancymobs.config;

import com.daxton.fancycore.api.config.ConfigLoad;
import com.daxton.fancymobs.FancyMobs;
import com.daxton.fancymobs.manager.MobManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MythicMobConfig {

	//讀取MythicMobs怪物設定檔
	public static void load(){

		if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null){
			Map<String, FileConfiguration> mob_Config_Map = ConfigLoad.execute("plugins\\MythicMobs\\Mobs");
			mob_Config_Map.forEach(MythicMobConfig::create);
		}

	}


	//建立怪物設定檔
	public static void create(String fileName, FileConfiguration loadConfig){
		File saveFile = new File(FancyMobs.fancyMobs.getDataFolder(), "Mobs/"+fileName);
		if(!saveFile.exists()){
			try {
				saveFile.createNewFile();
			}catch (IOException exception){
				exception.printStackTrace();
			}
		}
		FileConfiguration saveConfig = YamlConfiguration.loadConfiguration(saveFile);
		FileConfiguration mobDefaultConfig = FileConfig.config_Map.get("Default_Mob.yml");
		loadConfig.getConfigurationSection("").getKeys(false).forEach(mobID->{

			String type = loadConfig.getString(mobID+".Type");
			saveConfig.set(mobID +".Type", type);

			String display = loadConfig.getString(mobID+".Display");
			saveConfig.set(mobID +".Display", display);

			for(String key : mobDefaultConfig.getConfigurationSection("Default_Mob").getKeys(false)){
				if(!saveConfig.contains(mobID +".Custom."+ key)){
					String value =  mobDefaultConfig.getString("Default_Mob."+ key);
					saveConfig.set(mobID +".Custom."+ key, value);
				}
			}

			try {
				saveConfig.save(saveFile);
			}catch (IOException exception){
				exception.printStackTrace();
			}
			saveConfig.getConfigurationSection("").getKeys(false).forEach(mmobID->{
				Map<String, String> stats_Map = new ConcurrentHashMap<>();
				for(String key : saveConfig.getConfigurationSection(mmobID+".Custom").getKeys(false)){
					String value = saveConfig.getString(mmobID+".Custom."+key);
					stats_Map.put(key, value);
				}
				MobManager.mob_Stats_Map.put(mmobID, stats_Map);

			});
		});

	}

}
