package com.gmail.fateconquered.raceofkovo;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class Effects implements Listener {
    Map<String, Long> effectunderwater = new HashMap<>();
    Map<String, Long> alreadyhadeffect = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, 1, true, false));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location l = player.getLocation();
        l.setY(l.getY() + 1);
        Block block = l.getBlock();
        if(block.isEmpty()) {
            if(alreadyhadeffect.containsKey(player.getName())){
                alreadyhadeffect.remove(player.getName());
                //player.sendMessage("ХМ индикатора получения эффектов удалена!");
            }
            else{
               // player.sendMessage("ХМ индикатора не найдено, игрок на суше!");
            }
        }
        else if(block.isLiquid()) {
            if (!effectunderwater.containsKey(player.getName())) {
                if (alreadyhadeffect.containsKey(player.getName())){
                    //player.sendMessage("ХМ индикатора найдена, отмена выдачи ХМ получения эффектов!");
                }
                else if (!alreadyhadeffect.containsKey(player.getName())){
                    //player.sendMessage("ХМ получения эффектов выдана!");
                    effectunderwater.put(player.getName(), System.currentTimeMillis() + 15 * 1000);
                }
            }
            else if (effectunderwater.containsKey(player.getName())){
                if (effectunderwater.get(player.getName()) > System.currentTimeMillis()){
                player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING,  20, 0));
                //player.sendMessage("Вы получили эффект!");
                }
                else{
                    //player.sendMessage("ХМ получения эффектов удалена!");
                   // player.sendMessage("ХМ индикатора получения эффектов выдана!");
                    effectunderwater.remove(player.getName());
                    alreadyhadeffect.put(player.getName(), System.currentTimeMillis());
                }
            }
        }
    }
}