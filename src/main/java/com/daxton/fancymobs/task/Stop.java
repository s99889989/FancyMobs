package com.daxton.fancymobs.task;

import com.daxton.fancymobs.manager.MobManager;

public class Stop {

	public static void execute(){
		MobManager.fancy_Mob_Map.clear();
		MobManager.mob_Stats_Map.clear();
	}

}
