package com.daxton.fancymobs.api.placeholder;

import com.daxton.fancymobs.api.FancyMob;
import com.daxton.fancymobs.manager.MobManager;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public class FancyMobsPlaceholder {

	public static String valueOf(LivingEntity entity, String inputString){
		UUID uuid = entity.getUniqueId();
		FancyMob fancyMob = MobManager.fancy_Mob_Map.get(uuid);
		if(fancyMob != null){
			//獲取MM魔物ID
			if(inputString.toLowerCase().contains("<fc_mythic_id")){
				return fancyMob.getMythicID();
			}
			//獲取MM魔物等級
			if(inputString.toLowerCase().contains("<fc_mythic_level")){
				return String.valueOf(fancyMob.getMythicLevel());
			}
			//獲取MM魔物派系
			if(inputString.toLowerCase().contains("<fc_mythic_faction")){
				return fancyMob.getFaction();
			}

		}

		return "0";
	}

}
