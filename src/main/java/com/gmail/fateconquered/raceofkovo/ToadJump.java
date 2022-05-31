package com.gmail.fateconquered.raceofkovo;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class ToadJump implements Listener {
    Map<String, Long> jumpcooldown = new HashMap<>();
    Map<String, Long> damagecancel = new HashMap<>();
    Map<String, Long> risewalkspeed = new HashMap<>();


    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType().equals(Material.COAL)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(jumpcooldown.containsKey(player.getName())){
                    if(jumpcooldown.get(player.getName()) > System.currentTimeMillis()) {
                        long timeleft = ((jumpcooldown.get(player.getName()) - System.currentTimeMillis())/1000);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(timeleft + "c"));
                    }
                    else{
                        jumpcooldown.remove(player.getName());
                    }
                }
                else {
                    Vector dir = player.getLocation().getDirection();
                    Vector vec = new Vector(dir.getX() * 0.4D, 1.0D, dir.getZ() * 0.4D);
                    player.setVelocity(vec);
                    player.sendMessage("Вы совершили прыжок жабки!");
                    jumpcooldown.put(player.getName(), System.currentTimeMillis() + (20 * 1000));
                    damagecancel.put(player.getName(), System.currentTimeMillis());
                    risewalkspeed.put(player.getName(), System.currentTimeMillis() + (5 * 1000));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).isEmpty()) {
            if (damagecancel.containsKey(player.getName())) {
                //player.sendMessage("Блок отсутствует, ожидаем падения!");
            } else {
                damagecancel.remove(player.getName());
                //player.sendMessage("Блок получен, хэшмэп удалён!");
            }
        }
        else if(!player.getLocation().getBlock().getRelative(BlockFace.DOWN).isEmpty()){
            if (risewalkspeed.containsKey(player.getName())) {
                if (risewalkspeed.get(player.getName()) > System.currentTimeMillis()) {
                    player.setWalkSpeed((float) 0.8);
                    player.sendMessage("Скорость бега увеличена!");
                }
                else {
                    player.setWalkSpeed((float) 0.2);
                    player.sendMessage("Скорость бега очищена!");
                    risewalkspeed.remove(player.getName());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        Player player = (Player) event.getEntity();
        if(event.getEntity() instanceof Player){
            if(event.getCause() == EntityDamageEvent.DamageCause.FALL){
                if (damagecancel.containsKey(player.getName())){
                    event.setCancelled(true);
                    damagecancel.remove(player.getName());
                    // player.sendMessage("Урон от падения отменён!");

                }
            }
        }
    }
}

