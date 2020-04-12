package de.simpleolli.simplenick;

import de.simpleolli.simplenick.command.*;
import de.simpleolli.simplenick.listener.EntityDeathListener;
import de.simpleolli.simplenick.listener.PlayerJoinListener;
import de.simpleolli.simplenick.listener.PlayerQuitListener;
import de.simpleolli.simplenick.manager.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SimpleNick extends JavaPlugin {

    private static SimpleNick instance;
    private String prefix = "§8[§bNick§8] §7";

    private FileManager fileManager;
    private NickManager nickManager;
    private SkinManager skinManager;

    @Override
    public void onEnable() {
        try {
            fileManager.loadNicknames();
        } catch (IOException e) {
            e.printStackTrace();
        }
        registerCommands();
        registerListener();
    }

    @Override
    public void onLoad() {
        instance = this;
        nickManager = new NickManager();
        skinManager = new SkinManager();
        fileManager = new FileManager();
    }

    @Override
    public void onDisable() {

    }

    private void registerListener() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(),this);
        pluginManager.registerEvents(new PlayerQuitListener(),this);
        pluginManager.registerEvents(new EntityDeathListener(),this);
    }

    private void registerCommands() {
        getCommand("nick").setExecutor(new NickCommand());
        getCommand("nickList").setExecutor(new NickListCommand());
        getCommand("nickNames").setExecutor(new NickNamesCommand());
        getCommand("unnick").setExecutor(new UnnickCommand());
    }

    public String getUsage(final String usage) {
        return prefix + "Bitte verwende §b" + usage;
    }

    public String getNoPerms() {
        return prefix + "Dazu besitzt du nicht die nötige Berechtigung";
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public NickManager getNickManager() {
        return nickManager;
    }

    public SkinManager getSkinManager() {
        return skinManager;
    }

    public String getPrefix() {
        return prefix;
    }

    public static SimpleNick getInstance() {
        return instance;
    }
}
