package com.TopRank;

import org.bukkit.Bukkit;

public class TaskManager {
	static int time_Explane = 0, Explane_Scheduler = 0;
	static int time_Main = 0, Main_Scheduler = 0;
	static int time_Out = 0, Out_Scheduler = 0;
	static int time_Broadcast = 0, Broadcast_Scheduler = 0;
	static int time_ArenaMain = 0, ArenaMain_Scheduler = 0;
	static int time_ArenaOut = 0, ArenaOut_Scheduler = 0;

	public static void ClaerTimer(){
		time_Explane = 0; Explane_Scheduler = 0;
		time_Main = 0; Main_Scheduler = 0;
		time_Out = 0; Out_Scheduler = 0;
		time_Broadcast = 0; Broadcast_Scheduler = 0;
		time_ArenaMain = 0; ArenaMain_Scheduler = 0;
		time_ArenaOut = 0; ArenaOut_Scheduler = 0;
		Main.GAME_START = false;
		Main.ARENA_MODE = false;
	}

	public static void ExplaneTimer(Main main) {
		int Dealy = 15;
		
		Explane_Scheduler = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
			public void run() {
				switch (time_Explane) {
				case 0:
					time_ArenaOut = 0;
					time_Out = 0;
					Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247a게임을 시작합니다.");
					break;
				case 1:
					if (Main.QUICK_START) StartTimer(main);
					break;
				case 2:
					Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247aTopRank는 게임 시간 동안 가장 높은 순위를 차지하면 승리하는 게임입니다.");
					break;
				case 3:
					break;
				case 4:
					Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247a자신보다 한 단계 위 플레이어를 죽이면, 승급할 수 있습니다.");
					break;
				case 5:
					break;
				case 6:
					if (Main.TIMER_OUT >= 1)
						Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247a게임 시작 후 " + Main.TIMER_OUT + "초마다 가장 낮은 순위의 플레이어는 탈락합니다.");
					break;
				case 7:
					break;
				case 8:
					Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247a또한, 1위 플레이어가 가장 낮은 순위의 플레이어를 죽이면, 해당 플레이어가 탈락합니다.");
					break;
				case 9:
					break;
				case 10:
					Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247a게임 시작 이후 특정 조건을 만족하면 ARENA MODE에 진입합니다.");
					break;
				case 11:
					break;
				case 12:
					if (Main.ARENA_OUT_ACTIVE != true)
						Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247aARENA MODE에서는 1위 플레이어가 최하 플레이어를 탈락시킬 수 없으며, " + Main.ARENA_TIMER_OUT + "초마다 가장 낮은 순위의 플레이어는 탈락합니다.");
					else
						Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247aARENA MODE에서는 " + Main.ARENA_TIMER_OUT + "초마다 가장 낮은 순위의 플레이어는 탈락합니다.");
					break;
				case 13:
					break;
				case 14:
					if (Main.FIRST_SHOW_RANKING != true)
						Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247a잠시 후, 전체 순위 발표화 함께 게임이 시작됩니다.");
					else 
						Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247a잠시 후, 게임이 시작됩니다.");
					break;
				case 15:
					break;
				case 16:
					break;
				case 17:
					break;
				case 18:
					Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247a5");
					break;
				case 19:
					Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247a4");
					break;
				case 20:
					Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \24763");
					break;
				case 21:
					Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247c2");
					break;
				case 22:
					Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \24741");
					break;
				default:
					StartTimer(main);
				}
				time_Explane++;
			}
		}, 0, Dealy);
	}
	
	public static void StartTimer(Main main) {
		Main_Scheduler = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
			public void run() {

				switch (time_Main) {
				case 0:
					main.getServer().getScheduler().cancelTask(Explane_Scheduler);
					Main.GAME_START = true;
					PlayerManager.shuffleRank();
					PlayerManager.teleport();
					OutTimer(main);
					BroadcastTimer(main);
					break;
				case 3:
					if (Main.FIRST_SHOW_RANKING) {
						PlayerManager.showRanking();
					}
					break;
				default:
					PlayerManager.setExp();
					boolean arenaTime = true;
					boolean arenaCount = true;
					if (Main.ARENA_START_TIME >= 0) {
						if (Main.ARENA_START_TIME * 60 > time_Main) arenaTime = false;
					}
					if (Main.ARENA_START_PERSON >= 0) {
						if (Main.ARENA_START_PERSON <= PlayerManager.RemainPlayer) arenaCount = false;
					}
					if (arenaTime && arenaCount) {
						ArenaStartTimer(main);
					}
					if (Main.DEBUG_SCHEDULER)
						Bukkit.getConsoleSender().sendMessage("[TopRank] arenaTime : " + arenaTime + ", arenaCount : " + arenaCount);
				}
				if (Main.DEBUG_SCHEDULER)
					Bukkit.getConsoleSender().sendMessage("[TopRank] Main : " + String.valueOf(time_Main));
				time_Main++;
			}
		}, 0, 20);
	}
	
	public static void ArenaStartTimer(Main main) {
		ArenaMain_Scheduler = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
			public void run() {
				main.getServer().getScheduler().cancelTask(Main_Scheduler);
				main.getServer().getScheduler().cancelTask(Out_Scheduler);
				PlayerManager.setExp();
				if (time_ArenaMain == 0) {
					Main.ARENA_MODE = true;
					Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \2476ARENA MODE");
					PlayerManager.ArenaTeleport();
					ArenaOutTimer(main);
				}
				if (Main.DEBUG_SCHEDULER)
					Bukkit.getConsoleSender().sendMessage("[TopRank] ArenaMain : " + String.valueOf(time_ArenaMain));
				time_ArenaMain++;
			}
		}, 0, 20);
	}
	
	public static void BroadcastTimer(Main main) {
		if (Main.TIMER_SHOW_RANKING >= 1) {	
			Broadcast_Scheduler = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
				public void run() {
					if (time_Broadcast != 0) {
						PlayerManager.showRanking();
					}
					if (Main.DEBUG_SCHEDULER)
						Bukkit.getConsoleSender().sendMessage("[TopRank] Broadcast : " + String.valueOf(time_Broadcast));
					time_Broadcast++;
				}
			}, 0, Main.TIMER_SHOW_RANKING * 20);
		}
	}
	
	public static void OutTimer(Main main) {
		if (Main.GAME_START) {
			if (Main.TIMER_OUT >= 1) {
				Out_Scheduler = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
					public void run() {
						if (!Main.GAME_START) {
							time_Out = 0;
							main.getServer().getScheduler().cancelTask(Out_Scheduler);
							return;
						}
						if (time_Out != 0) {
							PlayerManager.playerOut();
						}
						if (Main.DEBUG_SCHEDULER)
							Bukkit.getConsoleSender().sendMessage("[TopRank] Out : " + String.valueOf(time_Out));
					time_Out++;
					}
				}, 0, Main.TIMER_OUT * 20);
			}
		}
	}
	
	public static void ArenaOutTimer(Main main) {
		if (Main.GAME_START) {
			if (Main.ARENA_TIMER_OUT >= 1) {
				ArenaOut_Scheduler = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
					public void run() {
						if (!Main.GAME_START) {
							time_ArenaOut = 0;
							main.getServer().getScheduler().cancelTask(ArenaOut_Scheduler);
							return;
						}
						if (time_ArenaOut == 0)
							main.getServer().getScheduler().cancelTask(Out_Scheduler);
						else {
							PlayerManager.playerOut();
						}
						if (Main.DEBUG_SCHEDULER)
							Bukkit.getConsoleSender().sendMessage("[TopRank] ArenaOut : " + String.valueOf(time_ArenaOut));
						time_ArenaOut++;
					}
				}, 0, Main.ARENA_TIMER_OUT * 20);
			}
		}
	}
}
