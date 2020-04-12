package de.simpleolli.simplenick.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerNickEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private String realName;
    private String nickName;
    private Location respawnLocation;

    public PlayerNickEvent(Player player, String realName, String nickName, Location respawnLocation) {
        this.player = player;
        this.realName = realName;
        this.nickName = nickName;
        this.respawnLocation = respawnLocation;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Location getRespawnLocation() {
        return respawnLocation;
    }

    public void setRespawnLocation(Location respawnLocation) {
        this.respawnLocation = respawnLocation;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
