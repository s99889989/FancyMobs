package com.daxton.fancymobs.listener;


import com.daxton.fancycore.api.aims.entity.Convert;
import com.daxton.fancycore.hook.Vault.Currency;
import com.daxton.fancymobs.FancyMobs;
import com.daxton.fancymobs.api.FancyMob;
import com.daxton.fancymobs.api.event.FancyMobDeathEvent;
import com.daxton.fancymobs.api.event.FancyMobSpawnEvent;
import com.daxton.fancymobs.manager.MobManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;


public class MobListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)//當生物出生
    public void MobSpawn(EntitySpawnEvent event){
        Entity entity = event.getEntity();
        UUID uuid = entity.getUniqueId();

        new BukkitRunnable() {
            @Override
            public void run() {
                MobManager.fancy_Mob_Map.putIfAbsent(uuid, new FancyMob(entity));
                FancyMob fancyMob = MobManager.fancy_Mob_Map.get(uuid);
                if(!fancyMob.isMythicMob()){
                    FancyMobSpawnEvent fancyMobSpawnEvent = new FancyMobSpawnEvent(fancyMob);
                    Bukkit.getPluginManager().callEvent(fancyMobSpawnEvent);
                    //FancyMobs.fancyMobs.getLogger().info("原生生物出生"+uuid);
                }
            }
        }.runTaskLater(FancyMobs.fancyMobs, 2);
    }

    @EventHandler(priority = EventPriority.LOW )//當怪物受到攻擊
    public void onAttack(EntityDamageByEntityEvent event){
        Entity attacker = Convert.convertEntity(event.getDamager());
        Entity attacked = event.getEntity();
        //FancyMobs.fancyMobs.getLogger().info(attacker.getType().toString());
        if(!(attacker instanceof Player) || !(attacked instanceof LivingEntity) || attacked instanceof Player){
            return;
        }

        UUID uuid = attacked.getUniqueId();
        FancyMob fancyMob = MobManager.fancy_Mob_Map.get(uuid);
        if(fancyMob != null && !fancyMob.isMythicMob()){
            double damageNumber = event.getFinalDamage();
            UUID attackerUUID = attacker.getUniqueId();
            fancyMob.setThreat(attackerUUID, damageNumber);

//            LivingEntity livingAttacked = (LivingEntity) attacked;
//            double nowHealth = livingAttacked.getHealth();
//            if(damageNumber >= nowHealth){
//                new BukkitRunnable() {
//                    @Override
//                    public void run() {
//                        FancyMobs.fancyMobs.getLogger().info("攻擊怪物死亡");
//                    }
//                }.runTaskLater(FancyMobs.fancyMobs, 2);
//
//            }

        }


    }

    @EventHandler(priority = EventPriority.LOW)//怪物死亡
    public void onDeath(EntityDeathEvent event) {
//        if(!(event.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
//            return;
//        }
        if(event.getEntity().getKiller() != null){
            Player killer = event.getEntity().getKiller();
            Entity mob = event.getEntity();
            UUID mobUUID = mob.getUniqueId();
            FancyMob fancyMob = MobManager.fancy_Mob_Map.get(mobUUID);
            if(fancyMob != null && !fancyMob.isMythicMob()){
               // FancyMobs.fancyMobs.getLogger().info("怪物死亡");
                fancyMob.setDropItems(event.getDrops());
                fancyMob.setExp(event.getDroppedExp());

                FancyMobDeathEvent fancyMobDeathEvent = new FancyMobDeathEvent(fancyMob, killer);
                Bukkit.getPluginManager().callEvent(fancyMobDeathEvent);
                //修改掉落經驗
                event.setDroppedExp(fancyMobDeathEvent.getDropExp());
                //修改掉落物
                Location location = mob.getLocation();
                fancyMobDeathEvent.getDropItems().forEach(itemStack -> {
                    location.getWorld().dropItemNaturally(location, itemStack, item -> item.setThrower(mobUUID));
                });
                event.getDrops().clear();
                //修改掉落金錢
                Currency.giveMoney(killer, fancyMobDeathEvent.getMoney());

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        MobManager.fancy_Mob_Map.remove(mobUUID);
                    }
                }.runTaskLater(FancyMobs.fancyMobs, 20);

            }else {
                event.setDroppedExp(0);
            }
        }

    }


//    @EventHandler(priority = EventPriority.LOW)//怪物出生
//    public void onFancyMobSpawn(FancyMobSpawnEvent event){
//
//    }
//
//    @EventHandler(priority = EventPriority.LOW)//怪物死亡
//    public void onFancyMobDeath(FancyMobDeathEvent event){
//        Player killer = event.getKiller();
//        killer.sendMessage("怪物死亡2");
//        List<ItemStack> dropItems = new ArrayList<>();
//        dropItems.add(new ItemStack(Material.PLAYER_HEAD));
//        event.setDropItems(dropItems);
//        event.setMoney(10);
//        event.setDropExp(10);
//
//        event.getDropItems().forEach(itemStack ->  killer.sendMessage("獲得物品: "+itemStack.getType()));
////        FancyMobs.fancyMobs.getLogger().info("獲得經驗: "+event.getDropExp());
////        FancyMobs.fancyMobs.getLogger().info("獲得金錢: "+event.getMoney());
//
//        killer.sendMessage("目前經驗:" + killer.getTotalExperience());
//        killer.sendMessage("目前存款: " + Currency.getMoneyAmount(killer));
//    }


}
