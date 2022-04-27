package com.daxton.fancymobs.api;

import com.daxton.fancymobs.manager.MobManager;

import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FancyMob {

	//魔物ID，如果是MythicMob就用MythicMobID，如果不是就用生物名稱
	private String mobID;
	//實體
	private final Entity entity;
	//MythicMob實體
	private ActiveMob activeMob;
	//是否為MythicMob
	private final boolean mythicMob;
	//威脅表
	private Map<UUID, Double> threatTable = new HashMap<>();
	//掉落物品
	private List<ItemStack> dropItems;
	//掉落經驗
	private int exp = 0;
	//掉落金錢
	private double money = 0;
	//自訂屬性值
	private Map<String, String> stats = new HashMap<>();
	//設置原生魔物
	public FancyMob(Entity entity){
		this.mythicMob = false;
		this.entity = entity;

		this.mobID = entity.getName();
		if(MobManager.mob_Stats_Map.containsKey(entity.getName())){
			stats.putAll(MobManager.mob_Stats_Map.get(entity.getName()));
		}

	}

	//設置MythicMob魔物
	public FancyMob(ActiveMob activeMob){
		this.activeMob = activeMob;
		this.entity = activeMob.getEntity().getBukkitEntity();
		this.mythicMob = true;
		this.mobID = activeMob.getMobType();
		if(MobManager.mob_Stats_Map.containsKey(activeMob.getMobType())){
			stats.putAll(MobManager.mob_Stats_Map.get(activeMob.getMobType()));
		}
	}
	//獲取實體
	public Entity getEntity() {
		return entity;
	}
	//獲取MythicMob實體
	public ActiveMob getActiveMob() {
		return activeMob;
	}
	//設置威脅表
	public void setThreat(UUID uuid, double amount){
		if(threatTable.get(uuid) == null){
			threatTable.put(uuid, amount);
		}else {
			double nowAmount = threatTable.get(uuid);
			nowAmount += amount;
			threatTable.put(uuid, nowAmount);
		}
	}
	//獲取威脅表
	public Map<UUID, Double> getThreatTable() {
		return threatTable;
	}
	//獲取掉落物列表
	public List<ItemStack> getDropItems() {
		return dropItems;
	}
	//設置掉落物列表
	public void setDropItems(List<ItemStack> dropItems) {
		this.dropItems = dropItems;
	}
	//獲取掉落經驗
	public int getDropExp() {
		return exp;
	}
	//設置掉落經驗
	public void setExp(int exp) {
		this.exp = exp;
	}
	//獲取掉落金錢
	public double getMoney() {
		return money;
	}
	//設置掉落金錢
	public void setMoney(double money) {
		this.money = money;
	}
	//是否為MythicMobs魔物
	public boolean isMythicMob() {
		return mythicMob;
	}
	//獲取MythicMobs等級
	public double getMythicLevel(){
		if(isMythicMob()){
			return activeMob.getLevel();
		}
		return 0;
	}
	//設置MythicMobs等級
	public void setMythicLevel(double level){
		if(isMythicMob()){
			activeMob.setLevel(level);
		}
	}
	//獲取MythicMobsID
	public String getMythicID(){
		if(isMythicMob()){
			activeMob.getMobType();
		}
		return "";
	}
	//獲取MythicMobs派系
	public String getFaction(){
		if(isMythicMob()){
			activeMob.getFaction();
		}
		return "";
	}

	//設置自訂屬性
	public void setCustomValue(String key, String value){
		if(stats.containsKey(key)){
			stats.put(key, value);
		}
	}
	//初始定自屬性
	public void setDefaultCustomValue(String key){
		if(stats.containsKey(key)){
			Map<String, String> defValue = MobManager.mob_Stats_Map.get(activeMob.getMobType());
			stats.put(key, defValue.get(key));
		}
	}

	//獲取自訂屬性值
	public String getCustomValue(String key){
		if(stats.containsKey(key)){
			return stats.get(key);
		}
		return "0";
	}
	//設置自訂屬性值
	public void setStats(String key,String value){
		stats.put(key, value);
	}

	public String getMobID() {
		return mobID;
	}
}
