package com.daxton.fancymobs.api.event;

import com.daxton.fancymobs.api.FancyMob;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class FancyMobDeathEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private final FancyMob fancyMob;

	private Player killer;

	private List<ItemStack> dropItems;

	private Map<UUID, Double> threatTable;

	private int exp = 0;

	private double money = 0;

	public FancyMobDeathEvent(FancyMob fancyMob, Player killer){
		this.killer = killer;
		this.fancyMob = fancyMob;
		this.dropItems = fancyMob.getDropItems();
		this.exp = fancyMob.getDropExp();
		this.threatTable = fancyMob.getThreatTable();
		this.money = fancyMob.getMoney();
	}
	//獲取FancyMob
	public FancyMob getFancyMob() {
		return fancyMob;
	}
	//獲取攻擊玩家
	public Player getKiller() {return killer;}
	//獲取掉落經驗
	public int getDropExp() {
		return exp;
	}
	//獲取掉落物品
	public List<ItemStack> getDropItems() {
		return dropItems;
	}
	//獲取掉落金錢
	public double getMoney() {return money;}
	//獲取仇恨表
	public Map<UUID, Double> getThreatTable() {
		return threatTable;
	}

	//設置掉落物品
	public void setDropItems(List<ItemStack> dropItems) {
		this.dropItems = dropItems;
	}
	//設置掉落經驗
	public void setDropExp(int dropExp) {
		this.exp = dropExp;
	}
	//設置掉落金錢
	public void setMoney(int money){this.money = money;}

	//清空物品
	public void removeItems(){
		this.dropItems.clear();
	}
	//清空錢
	public void removeMoney(){
		this.money = 0;
	}
	//清空
	public void removeExp(){
		this.exp = 0;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}

}
