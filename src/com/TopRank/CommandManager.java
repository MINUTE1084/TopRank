package com.TopRank;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
	public final Main main;
	
	public CommandManager(Main main_) {
		main = main_;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("toprank")) {
			if (args.length == 0) {
				sender.sendMessage("\2476-------\2471[\247bTopRank\2471]\2477\2476-------");
				sender.sendMessage("\2476/toprank \247f: \247a명령어를 확인합니다.");
				sender.sendMessage("\2476/toprank \247bstart \247f: \247a게임을 시작합니다.");
				sender.sendMessage("\2476/toprank \247bstop \247f: \247a게임을 중단합니다.");
				sender.sendMessage("\2476/toprank \247bshow \247f: \247a순위를 확인합니다.");
				sender.sendMessage("\2476/toprank \247bout \247f: \247a최하 플레이어를 탈락시킵니다.");
				sender.sendMessage("\2476/toprank \247btp \247f: \247a게임 시작시 랜덤으로 텔레포트 될 장소를 정합니다.");
				sender.sendMessage("\2476/toprank \247btpr \247f: \247a텔레포트 될 장소를 초기화 합니다.");
				sender.sendMessage("\2476/toprank \247batp \247f: \247aARENA MODE 시작 시 텔레포트 될 장소를 정합니다.");
			}
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("start")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (!p.isOp()) {
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247c권한이 부족합니다.");
							return false;
						}
					}
					if (PlayerManager.arenaLoc == null || PlayerManager.locs.size() <= 0) {
						Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247c텔레포트 장소가 설정 되어있지 않습니다.");
						return false;
					}
					if (PlayerManager.PlayerDatas.size() <= 2) {
						Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247c3명 이상 있을 때 게임을 시작 할 수 있습니다.");
						return false;
					}
					TaskManager.ExplaneTimer(main);
				}
				if (args[0].equalsIgnoreCase("stop")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (!p.isOp()) {
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247c권한이 부족합니다.");
							return false;
						}
					}
					Bukkit.getScheduler().cancelAllTasks();
					Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247c게임을 중단했습니다.");
					return true;
				}
				
				if (args[0].equalsIgnoreCase("show")) {
					PlayerManager.showRanking(sender);
					return true;
				}
				
				if (args[0].equalsIgnoreCase("out")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (!p.isOp()) {
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247c권한이 부족합니다.");
							return false;
						}
					}
					PlayerManager.playerOut();
					return true;
				}
				
				if (args[0].equalsIgnoreCase("tp")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (!p.isOp()) {
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247c권한이 부족합니다.");
							return false;
						}
						PlayerManager.locs.add(p.getLocation());
						sender.sendMessage("\2471[\247bTopRank\2471] \2477현재 좌표를 추가했습니다.");
						return true;
					}
					else sender.sendMessage("\2471[\247bTopRank\2471] \2477플레이어만 설정 가능합니다.");
				}
				
				if (args[0].equalsIgnoreCase("tpr")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (!p.isOp()) {
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247c권한이 부족합니다.");
							return false;
						}
					}
					PlayerManager.locs = new ArrayList<Location>();
					sender.sendMessage("\2471[\247bTopRank\2471] \2477좌표를 초기화했습니다.");
				}
				
				if (args[0].equalsIgnoreCase("atp")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (!p.isOp()) {
							Bukkit.broadcastMessage("\2471[\247bTopRank\2471] \247c권한이 부족합니다.");
							return false;
						}
						PlayerManager.arenaLoc = p.getLocation();
						sender.sendMessage("\2471[\247bTopRank\2471] \2477현재 좌표를 설정했습니다.");
					}
					else sender.sendMessage("\2471[\247bTopRank\2471] \2477플레이어만 설정 가능합니다.");
				}
			}
		}
		return true;
	}
}
