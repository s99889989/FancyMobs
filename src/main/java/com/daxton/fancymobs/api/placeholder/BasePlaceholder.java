package com.daxton.fancymobs.api.placeholder;

import com.daxton.fancymobs.api.FancyMob;
import com.daxton.fancymobs.manager.MobManager;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public class BasePlaceholder {


	public static String valueOf(LivingEntity entity, String inputString){
		UUID uuid = entity.getUniqueId();
		FancyMob fancyMob = MobManager.fancy_Mob_Map.get(uuid);
		if(fancyMob != null){
			//獲取MM魔物自訂屬性
			if(inputString.toLowerCase().contains("<fc_base_value_")){
				String key = inputString.replace(" ","").replace("<fc_base_value_","");
				return fancyMob.getCustomValue(key);
			}

		}

		return "0";
	}

}
