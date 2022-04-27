package com.daxton.fancymobs;


import com.daxton.fancymobs.listener.MythicMobListener;
import org.bukkit.Bukkit;

import static com.daxton.fancymobs.config.FileConfig.languageConfig;

public class DependPlugins {

    public static boolean depend(){

        FancyMobs fancyMobs = FancyMobs.fancyMobs;

        if (Bukkit.getServer().getPluginManager().getPlugin("FancyCore") != null && Bukkit.getPluginManager().isPluginEnabled("FancyCore")){
            fancyMobs.getLogger().info(languageConfig.getString("LogMessage.LoadFancyCore"));
        }else {
            if(languageConfig != null){
                languageConfig.getStringList("LogMessage.UnLoadFancyCore").forEach(message-> fancyMobs.getLogger().info(message));
            }else {
                fancyMobs.getLogger().info("§4*** FancyCore is not installed or not enabled. ***");
                fancyMobs.getLogger().info("§4*** FancyAction will be disabled. ***");
            }
            return false;
        }
        if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null && Bukkit.getPluginManager().isPluginEnabled("MythicMobs")) {
            fancyMobs.getLogger().info(languageConfig.getString("LogMessage.LoadMythicMobs"));
            Bukkit.getPluginManager().registerEvents(new MythicMobListener(), fancyMobs);
        }
        if (Bukkit.getServer().getPluginManager().getPlugin("ModelEngine") != null && Bukkit.getPluginManager().isPluginEnabled("ModelEngine")) {
            fancyMobs.getLogger().info(languageConfig.getString("LogMessage.LoadModelEngine"));
        }
        return true;
    }

}
