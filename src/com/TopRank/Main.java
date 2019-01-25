package com.TopRank;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public static PlayerManager PManager;
	
	public static boolean GAME_START = false;
	public static boolean DEBUG_SCHEDULER = false;
	public static boolean QUICK_START = true;
	public static boolean FIRST_SHOW_RANKING = true;
	public static boolean FIRST_CLEAR_INV = true;
	public static boolean USE_EXP_BAR = true;
	public static boolean SHOW_CHANGE_RANKING = true;
	public static boolean FIRST_GIVE_ITEM = true;
	public static int TIMER_SHOW_RANKING = 30;
	public static int PANELTY_TIME = 10;
	public static int TIMER_OUT = 120;
	
	
	public static boolean ARENA_MODE = false;
	public static boolean ARENA_OUT_ACTIVE = false;
	public static int ARENA_START_TIME = -1;
	public static int ARENA_START_PERSON = 3;
	public static int ARENA_TIMER_OUT = 15;

	public static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getConsoleSender().sendMessage("\2471[\247bTopRank\2471] \2477v0.1 활성화 되었어요.");
        System.out.println("Made by MINUTE.");
        
		getCommand("toprank").setExecutor(new CommandManager(this));
        getServer().getPluginManager().registerEvents(new EventManager(), this);
        
        getConfig().options().copyDefaults(true);
		saveConfig();

		QUICK_START = getConfig().getBoolean("빠른 시작"); // O
		DEBUG_SCHEDULER = getConfig().getBoolean("스케쥴러 디버그"); // O
		FIRST_SHOW_RANKING = getConfig().getBoolean("게임 시작 시 순위 공개"); // O
		FIRST_CLEAR_INV = getConfig().getBoolean("게임 시작 시 인벤토리 초기화"); // O
		USE_EXP_BAR = getConfig().getBoolean("경험치 바 사용"); // O
		SHOW_CHANGE_RANKING = getConfig().getBoolean("순위 교체 시 순위 교체 공개"); // O
		FIRST_GIVE_ITEM = getConfig().getBoolean("게임 시작 시 돌검 지급"); // O
		TIMER_SHOW_RANKING = getConfig().getInt("순위 공개 타이머"); 
		PANELTY_TIME = getConfig().getInt("패널티 시간");  // O
		TIMER_OUT = getConfig().getInt("탈락 시간"); // O?
		ARENA_OUT_ACTIVE = getConfig().getBoolean("아레나 모드 1위 탈락"); // O
		ARENA_START_TIME = getConfig().getInt("아레나 모드 활성화 시간"); 
		ARENA_START_PERSON = getConfig().getInt("아레나 모드 활성화 인원"); 
		ARENA_TIMER_OUT = getConfig().getInt("아레나 모드 탈락 시간"); // O
		
		if (ARENA_TIMER_OUT <= -1) {
			ARENA_OUT_ACTIVE = true;
		}


		System.out.println("빠른 시작 : " + String.valueOf(QUICK_START));
		System.out.println("게임 시작 시 순위 공개 : " + String.valueOf(FIRST_SHOW_RANKING));
		System.out.println("게임 시작 시 인벤토리 초기화 : " + String.valueOf(FIRST_CLEAR_INV));
		System.out.println("경험치 바 사용 : " + String.valueOf(USE_EXP_BAR));
		System.out.println("순위 교체 시 순위 교체 공개 : " + String.valueOf(SHOW_CHANGE_RANKING));
		System.out.println("게임 시작 시 지급 아이템 : " + String.valueOf(FIRST_GIVE_ITEM));
		System.out.println("순위 공개 타이머 : " + String.valueOf(TIMER_SHOW_RANKING) + "초");
		System.out.println("패널티 시간 : " + String.valueOf(PANELTY_TIME) + "초");
		System.out.println("탈락 시간 : " + String.valueOf(TIMER_OUT) + "초");
		System.out.println("아레나 모드 1위 탈락 : " + String.valueOf(ARENA_OUT_ACTIVE));
		System.out.println("아레나 모드 활성화 시간 : " + String.valueOf(ARENA_START_TIME) + "분");
		System.out.println("아레나 모드 활성화 인원 : " + String.valueOf(ARENA_START_PERSON) + "명");
		System.out.println("아레나 모드 탈락 시간 : " + String.valueOf(ARENA_TIMER_OUT) + "초");
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			PlayerManager.addPlayer(p);
		}
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("\2471[\247bTopRank\2471] \2477v0.1 비활성화 되었어요.");
        System.out.println("Made by MINUTE.");
	}
}
