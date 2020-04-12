package de.simpleolli.simplenick.command;

import de.simpleolli.simplenick.SimpleNick;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickNamesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("nickNames")) {
            if (commandSender instanceof Player) {
                final Player player = (Player) commandSender;
                if (player.hasPermission("SimpleNick.nickNames")) {
                    if(args.length == 1) {
                        if(args[0].equalsIgnoreCase("list")) {
                            if (!SimpleNick.getInstance().getFileManager().getFileNickNamesAsList().isEmpty()) {
                                player.sendMessage(SimpleNick.getInstance().getPrefix() + "Momentan in der Datei eingetragene Nicknames:");
                                SimpleNick.getInstance().getFileManager().getFileNickNamesAsList().forEach(nickName -> {
                                    player.sendMessage(SimpleNick.getInstance().getPrefix() + " §8» §b" + nickName + " §8▎ " + (SimpleNick.getInstance().getNickManager().getAvailableNickNames().contains(nickName) ? "§aVerfügbar" : "§cVergeben"));
                                });
                            }else {
                                player.sendMessage(SimpleNick.getInstance().getPrefix() + "In der §bNicknames.txt §7befinden sich aktuell keine Nicknames!");
                            }
                        }
                    }else if(args.length == 2) {
                        if(args[0].equalsIgnoreCase("add")) {
                            SimpleNick.getInstance().getFileManager().addFileNickName(args[1]);
                            player.sendMessage(SimpleNick.getInstance().getPrefix() + "Der Nickname §b" + args[1] + " §7wurde in der §bNicknames.txt §7eingtragen");
                            player.sendMessage(SimpleNick.getInstance().getPrefix() + "Um diese Änderung wirkend zu machen ist ein §bReload §7erforderlich");
                        }
                    }else {
                        player.sendMessage(SimpleNick.getInstance().getUsage("/nickNames <list | add <Name>>"));
                    }
                }else {
                    player.sendMessage(SimpleNick.getInstance().getNoPerms());
                }
            }
        }
        return false;
    }
}
