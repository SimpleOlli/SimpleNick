package de.simpleolli.simplenick.listener;

import de.simpleolli.simplenick.SimpleNick;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void handle(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        SimpleNick.getInstance().getNickManager().nick(player,false,true);
    }


}
