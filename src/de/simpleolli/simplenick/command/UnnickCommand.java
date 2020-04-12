package de.simpleolli.simplenick.command;

import de.simpleolli.simplenick.SimpleNick;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnnickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("unnick")) {
            if (commandSender instanceof Player) {
                final Player player = (Player) commandSender;
                if (player.hasPermission("SimpleNick.use")) {
                    if(args.length == 0) {
                        if(SimpleNick.getInstance().getNickManager().getPlayerRealName().containsKey(player)) {
                            SimpleNick.getInstance().getNickManager().unnick(player, true);
                            player.sendMessage(SimpleNick.getInstance().getPrefix() + "Dein Nickname wurde entfernt");
                        }else {
                            player.sendMessage(SimpleNick.getInstance().getPrefix() + "Du bist momentan nicht genickt!");
                        }
                    }else {
                        player.sendMessage(SimpleNick.getInstance().getUsage("/unnick"));
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
