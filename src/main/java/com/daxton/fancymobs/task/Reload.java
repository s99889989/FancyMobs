package com.daxton.fancymobs.task;


import com.daxton.fancymobs.config.FileConfig;
import com.daxton.fancymobs.config.MobConfig;
import com.daxton.fancymobs.config.MythicMobConfig;

public class Reload {
    //重新讀取的一些任務
    public static void execute(){
        //設定檔
        FileConfig.reload();
        //讀取原生生物設定檔
        MobConfig.execute();
        //讀取MythicMobs的設定檔
        MythicMobConfig.load();


    }

}
