package com.TopRank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerManager {
	public static List<PlayerInfo> PlayerDatas = new ArrayList<PlayerInfo>();
	public static List<Location> locs = new ArrayList<Location>();
	public static Location arenaLoc = null;
	public static int RemainPlayer = 0;
	
	public static void addPlayer(Player p) {
		PlayerDatas.add(new PlayerInfo(p.getName(), p));
	}
	
	public static void removePlayer(PlayerInfo p) {
		PlayerDatas.remove(p);
	}
	
	public static void shuffleRank() {
		
		Random random = new Random();
		RemainPlayer = PlayerDatas.size();
		int[] randomRank = new int [RemainPlayer];
		
		for (int i = 1; i <= RemainPlayer; i++) {
			randomRank[i-1] = i;
		}
		
		for (int i = 0; i < 100; i++) {
			int randomArr = 1 + random.nextInt(RemainPlayer-1);
			int Temp = randomRank[0];
			randomRank[0] = randomRank[randomArr];
			randomRank[randomArr] = Temp;
		}
		
		for (int i = 0; i < RemainPlayer; i++) {
			PlayerDatas.get(i).setRank(randomRank[i]);
			if (Main.FIRST_CLEAR_INV) {
				PlayerDatas.get(i).getData().getInventory().setHelmet(new ItemStack(Material.AIR, 1));
				PlayerDatas.get(i).getData().getInventory().setChestplate(new ItemStack(Material.AIR, 1));
				PlayerDatas.get(i).getData().getInventory().setLeggings(new ItemStack(Material.AIR, 1));
				PlayerDatas.get(i).getData().getInventory().setBoots(new ItemStack(Material.AIR, 1));
				PlayerDatas.get(i).getData().getInventory().clear();
			}
			if (Main.FIRST_GIVE_ITEM)
				PlayerDatas.get(i).getData().getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
			PlayerDatas.get(i).getData().setHealth(20);
			PlayerDatas.get(i).getData().setGameMode(GameMode.SURVIVAL);
			PlayerDatas.get(i).getData().setFoodLevel(20);
		}
		
		Collections.sort(PlayerDatas, new MiniComparator());
		setExp();
	}
	
	public static void teleport() {
		Random random = new Random();
		Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247b5초의 공격 불가 시간을 가집니다.");
		for (PlayerInfo pl : PlayerDatas) {
			Location randomloc = locs.get(random.nextInt(locs.size()));
			pl.getData().teleport(randomloc);
			pl.getData().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
			pl.getData().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 49));
			if (pl.getRank() == 1)
				pl.getData().playSound(randomloc, Sound.ENTITY_FIREWORK_LARGE_BLAST, 100L, 1L);
		}
	}
	
	public static void ArenaTeleport() {
		Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247b5초의 공격 불가 시간을 가집니다.");
		for (PlayerInfo pl : PlayerDatas) {
			if (!pl.getOut()) {
				pl.getData().teleport(arenaLoc);
				pl.getData().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
				pl.getData().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 49));
				if (pl.getRank() == 1)
					pl.getData().playSound(arenaLoc, Sound.BLOCK_END_PORTAL_SPAWN, 100L, 1L);
			}
		}
	}
	
	public static void showRanking() {
		Bukkit.broadcastMessage("\2476-------\2471[\247bRanking\2471]\2477\2476-------");
		for (int i = 0; i < PlayerDatas.size(); i++) {
			if (!PlayerDatas.get(i).getOut()) {
				switch (PlayerDatas.get(i).getRank()) {
				case 1:
					Bukkit.broadcastMessage("\247b" + String.valueOf(PlayerDatas.get(i).getRank()) + "st : \247a" + PlayerDatas.get(i).getName());
					PlayerDatas.get(i).getData().playSound(arenaLoc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100L, 2L);
					break;
				case 2:
					Bukkit.broadcastMessage("\247e" + String.valueOf(PlayerDatas.get(i).getRank()) + "nd : \247a" + PlayerDatas.get(i).getName());
					break;
				case 3:
					Bukkit.broadcastMessage("\2477" + String.valueOf(PlayerDatas.get(i).getRank()) + "rd : \247a" + PlayerDatas.get(i).getName());
					break;
				default:
					Bukkit.broadcastMessage("\2478" + String.valueOf(PlayerDatas.get(i).getRank()) + "th : \247a" + PlayerDatas.get(i).getName());
					break;
				}
			}
		}
		for (int i = 0; i < PlayerDatas.size(); i++) {
			if (PlayerDatas.get(i).getOut()) {
				switch (PlayerDatas.get(i).getRank()) {
				case 1:
					Bukkit.broadcastMessage("\2478\247m" + String.valueOf(PlayerDatas.get(i).getRank()) + "st : " + PlayerDatas.get(i).getName());
					break;
				case 2:
					Bukkit.broadcastMessage("\2478\247m" + String.valueOf(PlayerDatas.get(i).getRank()) + "nd : " + PlayerDatas.get(i).getName());
					break;
				case 3:
					Bukkit.broadcastMessage("\2478\247m" + String.valueOf(PlayerDatas.get(i).getRank()) + "rd : " + PlayerDatas.get(i).getName());
					break;
				default:
					Bukkit.broadcastMessage("\2478\247m" + String.valueOf(PlayerDatas.get(i).getRank()) + "th : " + PlayerDatas.get(i).getName());
					break;
				}
			}
		}
	}
	
	public static void showRanking(CommandSender sender) {
		sender.sendMessage("\2476-------\2471[\247bRanking\2471]\2477\2476-------");
		for (int i = 0; i < PlayerDatas.size(); i++) {
			if (!PlayerDatas.get(i).getOut()) {
				switch (PlayerDatas.get(i).getRank()) {
				case 1:
					sender.sendMessage("\247b" + String.valueOf(PlayerDatas.get(i).getRank()) + "st : \247a" + PlayerDatas.get(i).getName());
					break;
				case 2:
					sender.sendMessage("\247e" + String.valueOf(PlayerDatas.get(i).getRank()) + "nd : \247a" + PlayerDatas.get(i).getName());
					break;
				case 3:
					sender.sendMessage("\2477" + String.valueOf(PlayerDatas.get(i).getRank()) + "rd : \247a" + PlayerDatas.get(i).getName());
					break;
				default:
					sender.sendMessage("\2478" + String.valueOf(PlayerDatas.get(i).getRank()) + "th : \247a" + PlayerDatas.get(i).getName());
					break;
				}
			}
		}
		for (int i = 0; i < PlayerDatas.size(); i++) {
			if (PlayerDatas.get(i).getOut()) {
				switch (PlayerDatas.get(i).getRank()) {
				case 1:
					sender.sendMessage("\2478\247m" + String.valueOf(PlayerDatas.get(i).getRank()) + "st : " + PlayerDatas.get(i).getName());
					break;
				case 2:
					sender.sendMessage("\2478\247m" + String.valueOf(PlayerDatas.get(i).getRank()) + "nd : " + PlayerDatas.get(i).getName());
					break;
				case 3:
					sender.sendMessage("\2478\247m" + String.valueOf(PlayerDatas.get(i).getRank()) + "rd : " + PlayerDatas.get(i).getName());
					break;
				default:
					sender.sendMessage("\2478\247m" + String.valueOf(PlayerDatas.get(i).getRank()) + "th : " + PlayerDatas.get(i).getName());
					break;
				}
			}
		}
	}
	
	public static void playerOut() {
		if (Main.GAME_START) {
			RemainPlayer--;
			PlayerDatas.get(RemainPlayer).setOut(true);
			Player outPlayer = PlayerDatas.get(RemainPlayer).getData();
			outPlayer.setHealth(0);
			outPlayer.playSound(outPlayer.getLocation(), Sound.ENTITY_LIGHTNING_IMPACT, 100, 0.55f);
			outPlayer.playSound(outPlayer.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 100, 1);
			outPlayer.getLocation().getWorld().strikeLightningEffect(outPlayer.getLocation());
			outPlayer.setGameMode(GameMode.SPECTATOR);
		
			PlayerDatas.get(RemainPlayer).getData().getLocation();
			Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \2478" + String.valueOf(PlayerDatas.get(RemainPlayer).getName()) + "\2477님이 탈락했습니다.");
			Collections.sort(PlayerDatas, new MiniComparator());
			setExp();
			if ((PlayerManager.RemainPlayer == Main.ARENA_START_PERSON) && !Main.ARENA_MODE) {
				TaskManager.ArenaStartTimer(Main.instance);
			}
			else if (PlayerManager.RemainPlayer == 1) {
				EndGame();
			}
		}
	}
	
	public static boolean ChangeRank(Player a, Player b) {
		PlayerInfo first = null;
		PlayerInfo second = null;
		
		for (PlayerInfo p : PlayerDatas) {
			if (p.getData() == a)
				first = p;
			if (p.getData() == b)
				second = p;
		}
		
		if (first != null && second != null) {
			if (!first.getOut() || !second.getOut()) {
				if (first.getRank() - second.getRank() == 1) {
					final int Temp = first.getRank();
					first.setRank(second.getRank());
					second.setRank(Temp);
					Collections.sort(PlayerDatas, new MiniComparator());
					setExp();
					first.getData().playSound(first.getData().getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 30L, 1.5f);
					if (Main.SHOW_CHANGE_RANKING) {
						Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247aRank Switch!");
						switch (first.getRank()) {
						case 1:
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247b" + first.getName() + " ( " + first.getRank() + "st )");
							break;
						case 2:
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \2476" + first.getName() + " ( " + first.getRank() + "nd )");
							break;
						case 3:
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \2477" + first.getName() + " ( " + first.getRank() + "rd )");
							break;
						default:
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \2478" + first.getName() + " ( " + first.getRank() + "th )");
							break;
						}
						switch (second.getRank()) {
						case 1:
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247b" + second.getName() + " ( " + second.getRank() + "st )");
							break;
						case 2:
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \2476" + second.getName() + " ( " + second.getRank() + "nd )");
							break;
						case 3:
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \2477" + second.getName() + " ( " + second.getRank() + "rd )");
							break;
						default:
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \2478" + second.getName() + " ( " + second.getRank() + "th )");
							break;
						}
					}
					return false;
				}
				else if (first.getRank() == 1 && second.getRank() == RemainPlayer) {
					if (Main.ARENA_MODE && !Main.ARENA_OUT_ACTIVE) return false;
					
					if (Main.ARENA_MODE) {
						Bukkit.getScheduler().cancelTask(TaskManager.ArenaOut_Scheduler);
						TaskManager.time_ArenaOut = 0;
						TaskManager.ArenaOutTimer(Main.instance);
					}
					else {
						Bukkit.getScheduler().cancelTask(TaskManager.Out_Scheduler);
						TaskManager.time_Out = 0;
						TaskManager.OutTimer(Main.instance);
					}
					playerOut();
					return false;
				}
				else return true;
			}
			else return false;
		}
		return false;
	}
	
	public static void setExp() {
		if (Main.USE_EXP_BAR) {
			for (PlayerInfo pl : PlayerDatas) {
				Player p = pl.getData();
				if (pl.getOut()) p.setLevel(0);
				else p.setLevel(pl.getRank());
			}
		}
	}

	public static void EndGame() {
		Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \2476GAME SET");
		for (PlayerInfo pl : PlayerDatas) {
			if (!pl.getOut()) {
				Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \2476" + pl.getName() + " Win!");
				pl.getData().playSound(pl.getData().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100L, 1L);
				pl.getData().playSound(pl.getData().getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 50L, 1L);
				pl.getData().spawnParticle(Particle.FIREWORKS_SPARK, pl.getData().getLocation(), 150, 0.25, 0.25, 0.25);
			}
		}
		Bukkit.getScheduler().cancelAllTasks();
		TaskManager.time_Explane = 0; TaskManager.Explane_Scheduler = 0;
		TaskManager.time_Main = 0; TaskManager.Main_Scheduler = 0;
		TaskManager.time_Out = 0; TaskManager.Out_Scheduler = 0;
		TaskManager.time_Broadcast = 0; TaskManager.Broadcast_Scheduler = 0;
		TaskManager.time_ArenaMain = 0; TaskManager.ArenaMain_Scheduler = 0;
		TaskManager.time_ArenaOut = 0; TaskManager.ArenaOut_Scheduler = 0;
		Main.GAME_START = false;
		Main.ARENA_MODE = false;
		for (PlayerInfo pl : PlayerDatas) {
			pl.setRank(0);
			pl.setOut(false);
			pl.getData().setGameMode(GameMode.SURVIVAL);
		}
	}
}

class PlayerInfo {
	private String PlayerName;
	private int PlayerRank = 0;
	private boolean isOut = false;
	private Player PlayerData;
	
	public PlayerInfo(String name, Player data) {
		 PlayerName = name;
		 PlayerRank = 0;
		 isOut = false;
		 PlayerData = data;
	}
	
	public PlayerInfo() {
		 PlayerName = " ";
		 PlayerRank = 0;
		 isOut = false;
		 PlayerData = null;
	}
	
	public String getName(){
		return PlayerName;
	}
	public void setName(String PlayerName_){
		PlayerName = PlayerName_;
	}
	
	public int getRank(){
		return PlayerRank;
	}
	public void setRank(int PlayerRank_){
		PlayerRank = PlayerRank_;
	}
	public boolean getOut(){
		return isOut;
	}
	public void setOut(boolean isOut_){
		isOut = isOut_;
	}
	public Player getData(){
		return PlayerData;
	}
	public void setData(Player PlayerData_){
		PlayerData = PlayerData_;
	}
}

class MiniComparator implements Comparator<PlayerInfo>{
	@Override
	public int compare(PlayerInfo a, PlayerInfo b) {
		int aValue = a.getRank();
		int bValue = b.getRank();

		if (aValue > bValue) {
			return 1;
		}
		else if (aValue < bValue) {
			return -1;
		}
		else {
			return 0;
		}
	}
}
