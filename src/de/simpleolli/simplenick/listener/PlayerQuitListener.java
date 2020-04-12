package de.simpleolli.simplenick.listener;

import de.simpleolli.simplenick.SimpleNick;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void handle(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if(SimpleNick.getInstance().getNickManager().getPlayerRealName().containsKey(player)) {
            SimpleNick.getInstance().getNickManager().getAvailableNickNames().add(player.getName());
            SimpleNick.getInstance().getSkinManager().getSkinCache().remove(SimpleNick.getInstance().getNickManager().getPlayerRealName().get(player));
            SimpleNick.getInstance().getNickManager().getPlayerRealName().remove(player);
        }else {
            SimpleNick.getInstance().getSkinManager().getSkinCache().remove(player.getName());
        }
    }

}
