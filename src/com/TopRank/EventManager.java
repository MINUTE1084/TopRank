package com.TopRank;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EventManager implements Listener {
	
	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent event)
	{
		if (!Main.GAME_START) {
			Player p = event.getPlayer();
			PlayerManager.addPlayer(p);
		}
	}
	
	@EventHandler
	public static void onPlayerQuit(PlayerQuitEvent event)
	{
		Player p = event.getPlayer();
		for (PlayerInfo pl : PlayerManager.PlayerDatas){
			if (pl.getData() == p) {
				PlayerManager.removePlayer(pl);
				return;
			}
		}
	}
	
	@EventHandler
	public static void onPlayerDeath(PlayerDeathEvent event)
	{
		if (Main.GAME_START) {
			Player p = event.getEntity();
			for (PlayerInfo pl : PlayerManager.PlayerDatas){
				if (pl.getData() == p && !pl.getOut()) {
					p.spigot().respawn();
					if (Main.ARENA_MODE) {
						p.teleport(PlayerManager.arenaLoc);
					}
					else {
						Random random = new Random();
						Location randomloc = PlayerManager.locs.get(random.nextInt(PlayerManager.locs.size()));
						p.teleport(randomloc);
					}
					return;
				}
			}
		}
	}
	
	@EventHandler
	public static void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if (Main.GAME_START) {

			Player damager;
			Player player;
			
			if ( event.getEntity() instanceof Player ) {
				player = (Player)event.getEntity();
				
				if (event.getDamager() instanceof Player)
					damager = (Player) event.getDamager();
				else if (event.getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getDamager();
					if (arrow.getShooter() instanceof Player)
						damager = (Player) arrow.getShooter();
					else return;
				}
				else if (event.getDamager() instanceof Snowball) {
					Snowball snow=(Snowball)event.getDamager();
					if(snow.getShooter() instanceof Player)
						damager = (Player) snow.getShooter();
					else return;
				}
				else if (event.getDamager() instanceof SmallFireball) {
					Snowball snow=(Snowball)event.getDamager();
					if(snow.getShooter() instanceof Player)
						damager = (Player) snow.getShooter();
					else return;
				}
				else if (event.getDamager() instanceof Fireball) {
					Fireball fireball=(Fireball)event.getDamager();
					if(fireball.getShooter() instanceof Player)
						damager = (Player) fireball.getShooter();
					else return;
				}
				else if (event.getDamager() instanceof SmallFireball) {
					SmallFireball smallfireball = (SmallFireball)event.getDamager();
					if(smallfireball.getShooter() instanceof Player)
						damager = (Player) smallfireball.getShooter();
					else return;
				}
				else return;
			}
			else return;
			
			if (player.getHealth() <= event.getDamage()) {
				if (PlayerManager.ChangeRank(damager, player) && Main.PANELTY_TIME > 0) {
					damager.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Main.PANELTY_TIME*20, 1));
					damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Main.PANELTY_TIME*20, 2));
					damager.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Main.PANELTY_TIME*20, 100));
					damager.sendMessage("\2471[\247bTopRank\2471] \247c잘못된 킬로 인해 \2474" + String.valueOf(Main.PANELTY_TIME) + "초 \247c동안 패널티를 받습니다!");
					damager.playSound(damager.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 10, 1.2f);
				}
			}
			else return;
		}
	}
}
