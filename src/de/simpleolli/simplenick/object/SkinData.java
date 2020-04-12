package de.simpleolli.simplenick.object;

public class SkinData {

    private String playerName;
    private String texture;
    private String value;
    private String signature;

    public SkinData(String playerName) {
        this.playerName = playerName;
    }

    public SkinData(String playerName, String texture, String value, String signature) {
        this.playerName = playerName;
        this.texture = texture;
        this.value = value;
        this.signature = signature;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
