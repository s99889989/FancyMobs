package com.daxton.fancymobs.listener;

import com.daxton.fancycore.hook.Vault.Currency;
import com.daxton.fancymobs.FancyMobs;
import com.daxton.fancymobs.api.FancyMob;
import com.daxton.fancymobs.api.event.FancyMobDeathEvent;
import com.daxton.fancymobs.api.event.FancyMobSpawnEvent;
import com.daxton.fancymobs.manager.MobManager;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.bukkit.events.MythicMobLootDropEvent;
import io.lumine.mythic.bukkit.events.MythicMobSpawnEvent;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class MythicMobListener implements Listener {

	@EventHandler(priority = EventPriority.LOW)//當怪物出生
	public void onMythicMobSpawn(MythicMobSpawnEvent event){
		ActiveMob activeMob = event.getMob();
		UUID uuid = activeMob.getUniqueId();

		MobManager.fancy_Mob_Map.putIfAbsent(uuid, new FancyMob(activeMob));
		FancyMob fancyMob = MobManager.fancy_Mob_Map.get(uuid);
		if(fancyMob.isMythicMob()){
			FancyMobs.fancyMobs.getLogger().info("0怪物等級: "+fancyMob.getActiveMob().getLevel());
			FancyMobs.fancyMobs.getLogger().info("1怪物等級: "+fancyMob.getMythicLevel());
			FancyMobSpawnEvent fancyMobSpawnEvent = new FancyMobSpawnEvent(fancyMob);
			Bukkit.getPluginManager().callEvent(fancyMobSpawnEvent);
			new BukkitRunnable() {
				@Override
				public void run() {
					FancyMobs.fancyMobs.getLogger().info("2怪物等級: "+fancyMob.getActiveMob().getLevel());
					FancyMobs.fancyMobs.getLogger().info("3怪物等級: "+fancyMob.getMythicLevel());
				}
			}.runTaskLater(FancyMobs.fancyMobs, 20);
		}

	}
	@EventHandler(priority = EventPriority.LOW)//當MythicMob的怪物死亡時
	public void onMythicMobDeath(MythicMobDeathEvent event){
		Entity mob = event.getEntity();
		LivingEntity killer = event.getKiller();
		if(!(killer instanceof Player)){
			return;
		}
		Player killerPlayer = (Player) killer;
		UUID mobUUID = mob.getUniqueId();
		FancyMob fancyMob = MobManager.fancy_Mob_Map.get(mobUUID);
		if(fancyMob != null && fancyMob.isMythicMob()){
			fancyMob.setDropItems(event.getDrops());
			//設置威脅表
			ActiveMob activeMob = fancyMob.getActiveMob();
			activeMob.getThreatTable().getAllThreatTargets().forEach(abstractEntity -> {
				UUID akUUID = abstractEntity.getBukkitEntity().getUniqueId();
				double threatAmount = activeMob.getThreatTable().getThreat(abstractEntity);
				fancyMob.setThreat(akUUID, threatAmount);
			});
			//
			FancyMobDeathEvent fancyMobDeathEvent = new FancyMobDeathEvent(fancyMob, killerPlayer);
			Bukkit.getPluginManager().callEvent(fancyMobDeathEvent);

			//修改掉落物
			Location location = mob.getLocation();
			fancyMobDeathEvent.getDropItems().forEach(itemStack -> location.getWorld().dropItemNaturally(location, itemStack, item -> item.setThrower(mobUUID)));
			event.getDrops().clear();
			//修改掉落經驗
			killerPlayer.giveExp(fancyMobDeathEvent.getDropExp());
			//修改掉落金錢
			Currency.giveMoney(killerPlayer, fancyMobDeathEvent.getMoney());

			new BukkitRunnable() {
				@Override
				public void run() {
					MobManager.fancy_Mob_Map.remove(mobUUID);
				}
			}.runTaskLater(FancyMobs.fancyMobs, 20);

		}

	}
	@EventHandler(priority = EventPriority.LOW)//在生成戰利品表之前調用
	public void onMythicMobLootDrop(MythicMobLootDropEvent event){
		Entity mob = event.getEntity();
		UUID deathUUID = mob.getUniqueId();

		FancyMob fancyMob = MobManager.fancy_Mob_Map.get(deathUUID);
		if(fancyMob != null && fancyMob.isMythicMob()){
			fancyMob.setExp(event.getExp());
			fancyMob.setMoney(event.getMoney());

			//修改掉落經驗
			event.setExp(0);
			//修改掉落金錢
			event.setMoney(0);
		}

	}
}
