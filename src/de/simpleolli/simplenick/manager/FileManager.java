package de.simpleolli.simplenick.manager;

import de.simpleolli.simplenick.SimpleNick;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private File nickNameFile = new File("plugins//SimpleNick//Nicknames.txt");
    private File nickNameDic = new File("plugins//SimpleNick");

    public void loadNicknames() throws IOException {
        if(!nickNameDic.exists()) {
            nickNameDic.mkdir();
        }
        if(!nickNameFile.exists()) {
            nickNameFile.createNewFile();
            Bukkit.getConsoleSender().sendMessage(SimpleNick.getInstance().getPrefix() + "Die §bNicknames.txt §7wurde §aerfolgreich §7erstellt");
            Bukkit.getConsoleSender().sendMessage(SimpleNick.getInstance().getPrefix() + "Trage Nicknames in sie ein, um sie vom Plugin laden zu lassen");
        }
        getNickNamesFromFile();
        Bukkit.getConsoleSender().sendMessage(SimpleNick.getInstance().getPrefix() + "Es wurden §b" + SimpleNick.getInstance().getNickManager().getAvailableNickNames().size() + " §7Nickname(s) geladen");
    }

    public List<String> getFileNickNamesAsList() {
        List<String> nickNames = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(nickNameFile));
            String nickName;
            while ((nickName = bufferedReader.readLine()) != null) {
                nickNames.add(nickName);
            }
            bufferedReader.close();
            return nickNames;
        } catch (IOException e) {

        }
        return null;
    }

    public void addFileNickName(final String nickName) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(nickNameFile,true));
            bufferedWriter.write(nickName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getNickNamesFromFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(nickNameFile));
        String nickName;
        while ((nickName = bufferedReader.readLine()) != null) {
            if(!nickName.equalsIgnoreCase(" "))
            System.out.println(nickName);
            SimpleNick.getInstance().getNickManager().getAvailableNickNames().add(nickName);
        }
        bufferedReader.close();
    }
}
