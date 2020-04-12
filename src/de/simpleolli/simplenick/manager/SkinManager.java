package de.simpleolli.simplenick.manager;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.simpleolli.simplenick.SimpleNick;
import de.simpleolli.simplenick.object.SkinData;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Scanner;

public class SkinManager {

    private HashMap<String, SkinData> skinCache = new HashMap<>();

    public void setPlayerSkin(final Player player, final String nickName, final boolean cacheSkin) {
        if(!skinCache.containsKey(nickName)) {
            SkinData skinData = getSkinFromMojang(nickName);
            if(skinData != null || skinData.getSignature() != null || skinData.getValue() != null) {
                safeDefaultSkin(player);
                updateGameProfile(player,skinData);
               if(cacheSkin) { skinCache.put(nickName,skinData); }
            }else {
                player.sendMessage(SimpleNick.getInstance().getPrefix() + "Der Skin konnte nicht geladen werden. Du spielst nun mit deinem normalen Skin");
            }
        }else {
            safeDefaultSkin(player);
            updateGameProfile(player,skinCache.get(nickName));
        }
    }

    public void setPlayerDefaultSkin(final Player player) {
        SkinData skinData = skinCache.get(player.getName());
        if(skinData != null || skinData.getSignature() != null || skinData.getValue() != null) {
            updateGameProfile(player,skinData);
        }else {
            player.sendMessage(SimpleNick.getInstance().getPrefix() + "Beim laden §bDeines §7Skins ist ein Fehler aufgetreten. Du spielst weiterhin mit dem deines Nicknames");
        }
    }

    private void safeDefaultSkin(final Player player) {
        GameProfile gameProfile = ((CraftPlayer)player).getProfile();
        SkinData skinData = new SkinData(player.getName());
        gameProfile.getProperties().get("textures").forEach(property -> {
            skinData.setTexture("textures");
            skinData.setSignature(property.getSignature());
            skinData.setValue(property.getValue());
        });
        skinCache.put(player.getName(),skinData);
    }

    private void updateGameProfile(final Player player, final SkinData skinData) {
        GameProfile gameProfile = ((CraftPlayer)player).getProfile();
        gameProfile.getProperties().clear();
        gameProfile.getProperties().put(skinData.getTexture(), new Property(skinData.getTexture(), skinData.getValue(), skinData.getSignature()));
    }

    private SkinData getSkinFromMojang(final String nickName) {
        String skinName = null;
        String value = null;
        String signature = null;

        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + getUUIDFromName(nickName) + "?unsigned=false");
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            connection.addRequestProperty("User-Agent", "Mozilla/5.0");
            connection.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            connection.addRequestProperty("Pragma", "no-cache");

            String jsonString = new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject  = (JSONObject) jsonParser.parse(jsonString);
            JSONArray properties = (JSONArray) (jsonObject).get("properties");
            for (int i = 0; i < properties.size(); i++) {
                JSONObject property = (JSONObject) properties.get(i);
                skinName = (String) property.get("name");
                value = (String) property.get("value");
                signature = property.containsKey("signature") ? (String) property.get("signature") : null;
            }
            return new SkinData(nickName,skinName,value,signature);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String getUUIDFromName(final String nickName) throws IOException, ParseException {
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + nickName);
        URLConnection connection = url.openConnection();
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        connection.addRequestProperty("User-Agent", "Mozilla/5.0");
        connection.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
        connection.addRequestProperty("Pragma", "no-cache");
        String jsonString = new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);

        return (String) jsonObject.get("id");
    }

    public HashMap<String, SkinData> getSkinCache() {
        return skinCache;
    }
}
