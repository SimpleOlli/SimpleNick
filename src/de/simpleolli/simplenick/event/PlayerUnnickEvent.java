package de.simpleolli.simplenick.event;


import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUnnickEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private Location respawnLocation;

    public PlayerUnnickEvent(Player player, Location respawnLocation) {
        this.player = player;
        this.respawnLocation = respawnLocation;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
