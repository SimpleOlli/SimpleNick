package de.simpleolli.simplenick.command;

import de.simpleolli.simplenick.SimpleNick;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("nick")) {
            if (commandSender instanceof Player) {
                final Player player = (Player) commandSender;
                if (player.hasPermission("SimpleNick.use")) {
                    if(!SimpleNick.getInstance().getNickManager().getPlayerRealName().containsKey(player)) {
                        SimpleNick.getInstance().getNickManager().nick(player, true, true);
                    }else {
                        player.performCommand("unnick");
                    }
                }else {
                    player.sendMessage(SimpleNick.getInstance().getNoPerms());
                }
            }else {

            }
        }
        return false;
    }
}
