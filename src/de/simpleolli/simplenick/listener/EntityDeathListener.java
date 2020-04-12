package de.simpleolli.simplenick.listener;

import de.simpleolli.simplenick.SimpleNick;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void handle(final EntityDeathEvent event) {
        if(event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();
            if(SimpleNick.getInstance().getNickManager().getPlayerRealName().containsKey(player)) {
                player.spigot().respawn();
            }else {
                System.out.println("debug1");
            }
        }
    }

}
