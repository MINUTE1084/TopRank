package com.TopRank;

import org.bukkit.entity.Player;

public class PlayerInfo {
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

    public String getName() {
        return PlayerName;
    }

    public void setName(String PlayerName_) {
        PlayerName = PlayerName_;
    }

    public int getRank() {
        return PlayerRank;
    }

    public void setRank(int PlayerRank_) {
        PlayerRank = PlayerRank_;
    }

    public boolean getOut() {
        return isOut;
    }

    public void setOut(boolean isOut_) {
        isOut = isOut_;
    }

    public Player getData() {
        return PlayerData;
    }

    public void setData(Player PlayerData_) {
        PlayerData = PlayerData_;
    }
}
