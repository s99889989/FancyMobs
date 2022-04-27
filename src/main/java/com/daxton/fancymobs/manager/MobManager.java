package com.daxton.fancymobs.manager;

import com.daxton.fancymobs.api.FancyMob;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MobManager {

	//FancyMob
	public static Map<UUID, FancyMob> fancy_Mob_Map = new HashMap<>();
	//mobID Stats_Map
	public static Map<String, Map<String, String>> mob_Stats_Map = new HashMap<>();

}
