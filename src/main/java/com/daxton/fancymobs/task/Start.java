package com.daxton.fancymobs.task;


import com.daxton.fancymobs.config.MobConfig;
import com.daxton.fancymobs.config.MythicMobConfig;

public class Start {
    //只在開服時執行的任務
    public static void execute(){

        //讀取原生生物設定檔
        MobConfig.execute();
        //讀取MythicMobs的設定檔
        MythicMobConfig.load();

    }

}
