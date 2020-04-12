package de.simpleolli.simplenick.manager;

import com.mojang.authlib.GameProfile;
import de.simpleolli.simplenick.SimpleNick;
import de.simpleolli.simplenick.event.PlayerNickEvent;
import de.simpleolli.simplenick.event.PlayerUnnickEvent;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NickManager {

    private List<String> availableNickNames = new ArrayList<>();
    private HashMap<Player,String> playerRealName = new HashMap<>();

    public void nick(final Player player, final boolean reloadPlayer, final boolean cacheSkin) {
        playerRealName.put(player,player.getName());
        String nickName = getRandomNickName();
        SimpleNick.getInstance().getNickManager().getAvailableNickNames().remove(nickName);

        if(nickName != null) {
            SimpleNick.getInstance().getSkinManager().setPlayerSkin(player,nickName,cacheSkin);
            setNameTag(player,nickName);
            PlayerNickEvent event = new PlayerNickEvent(player,playerRealName.get(player),nickName,player.getLocation());
            Bukkit.getPluginManager().callEvent(event);
            if(reloadPlayer) {
                updatePlayerTab(player);
                updatePlayer(player,event.getRespawnLocation());
            }
            player.sendMessage(SimpleNick.getInstance().getPrefix() + "Dein Nickname lautet: §b" + nickName);
            player.sendMessage(SimpleNick.getInstance().getPrefix() + "Mit §b/unnick §7kannst du diesen wieder entfernen");
        }else {
            player.sendMessage(SimpleNick.getInstance().getPrefix() + "Es wurde kein verwendbarer Nickname gefunden");
        }
    }

    public void unnick(final Player player, final boolean reloadPlayer) {
        availableNickNames.add(player.getName());
        setNameTag(player,playerRealName.get(player));
        SimpleNick.getInstance().getSkinManager().setPlayerDefaultSkin(player);
        PlayerUnnickEvent event = new PlayerUnnickEvent(player,player.getLocation());
        Bukkit.getPluginManager().callEvent(event);
        if(reloadPlayer) {
            updatePlayerTab(player);
            updatePlayer(player,event.getRespawnLocation());
        }
        playerRealName.remove(player);
    }

    private void setNameTag(final Player player, final String nickName) {
        GameProfile gameProfile = ((CraftPlayer)player).getProfile();
        try {
            Field field = gameProfile.getClass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(gameProfile," ");
            field.set(gameProfile,nickName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePlayer(final Player player, final Location respawnLocation) {
        player.teleport(Bukkit.getWorld("nick").getSpawnLocation());
        new BukkitRunnable() {
            @Override
            public void run() {
               player.teleport(respawnLocation);
            }
        }.runTaskLater(SimpleNick.getInstance(),1);
    }

    private void updatePlayerTab(final Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();

        PacketPlayOutPlayerInfo packetRemoveTab = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,entityPlayer);
        PacketPlayOutPlayerInfo packetAddTab = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,entityPlayer);

        sendPacket(packetRemoveTab,entityPlayer);
        sendPacket(packetAddTab,entityPlayer);

        Bukkit.getOnlinePlayers().forEach(players -> {
            players.hidePlayer(player);
            players.showPlayer(player);
        });
    }

    private String getRandomNickName() {
        if(!availableNickNames.isEmpty()) {
            int randomInt = new Random().nextInt(availableNickNames.size());
            return availableNickNames.get(randomInt);
        }
        return null;
    }

    private void sendPacket(final Packet<?> packet, final EntityPlayer entityPlayer) {
        entityPlayer.playerConnection.sendPacket(packet);
    }

    public List<String> getAvailableNickNames() {
        return availableNickNames;
    }

    public HashMap<Player, String> getPlayerRealName() {
        return playerRealName;
    }

}
