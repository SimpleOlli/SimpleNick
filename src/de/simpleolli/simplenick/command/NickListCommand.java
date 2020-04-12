package de.simpleolli.simplenick.command;

import de.simpleolli.simplenick.SimpleNick;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("nickList")) {
            if (commandSender instanceof Player) {
                final Player player = (Player) commandSender;
                if (player.hasPermission("SimpleNick.nickList")) {
                    if(args.length == 0) {
                        if(!SimpleNick.getInstance().getNickManager().getPlayerRealName().isEmpty()) {
                            player.sendMessage(SimpleNick.getInstance().getPrefix() + "Folgende Spieler sind momentan genickt:");
                            SimpleNick.getInstance().getNickManager().getPlayerRealName().keySet().forEach(players -> {
                                player.sendMessage(SimpleNick.getInstance().getPrefix() + players.getName() + " §8» §b" + SimpleNick.getInstance().getNickManager().getPlayerRealName().get(players));
                            });
                        }else {
                            player.sendMessage(SimpleNick.getInstance().getPrefix() + "Momentan ist kein Spieler genickt!");
                        }
                    }else {
                        player.sendMessage(SimpleNick.getInstance().getUsage("/nickList"));
                    }
                }else {
                    player.sendMessage(SimpleNick.getInstance().getNoPerms());
                }
            }
        }
        return false;
    }
}
