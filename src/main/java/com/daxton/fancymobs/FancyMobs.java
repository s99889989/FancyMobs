package com.daxton.fancymobs;

import com.daxton.fancymobs.command.MainCommand;
import com.daxton.fancymobs.command.TabCommand;
import com.daxton.fancymobs.config.FileConfig;
import com.daxton.fancymobs.listener.MobListener;
import com.daxton.fancymobs.task.Start;
import com.daxton.fancymobs.task.Stop;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static com.daxton.fancymobs.config.FileConfig.languageConfig;

public final class FancyMobs extends JavaPlugin {

    public static FancyMobs fancyMobs;

    @Override
    public void onEnable() {
        fancyMobs = this;
        //設定檔
        FileConfig.execute();
        if(!DependPlugins.depend()){
            fancyMobs.setEnabled(false);
            return;
        }
        //指令
        Objects.requireNonNull(Bukkit.getPluginCommand("fancymobs")).setExecutor(new MainCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("fancymobs")).setTabCompleter(new TabCommand());
        //開服執行任務
        Start.execute();
        //監聽
       // Bukkit.getPluginManager().registerEvents(new MobListener(), fancyMobs);


    }

    @Override
    public void onDisable() {

        Stop.execute();

        if(languageConfig != null){
            fancyMobs.getLogger().info(languageConfig.getString("LogMessage.Disable"));
        }
    }
}
