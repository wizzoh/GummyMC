package me.wizzo.gummymc.files;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigFile {

    private File file;
    private FileConfiguration conf;
    private final GummyMC main;
    private final String error = "&cErrore con il caricamento del file";

    public ConfigFile(GummyMC main) {
        this.main = main;
    }

    public void setup(GummyMC main, String fileName) {
        file = new File(main.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                boolean success = file.createNewFile();
                if (success) {
                    load();
                    defaultConfig();
                    save();
                } else {
                    main.getConsole().sendMessage(main.messageFormat(main.getPrefix() + error));
                }
            } catch (IOException e) {
                main.getConsole().sendMessage(main.messageFormat(main.getPrefix() + error));
            }
        } else {
            load();
            save();
        }
    }

    public void load() {
        try {
            conf = YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            main.getConsole().sendMessage(main.messageFormat(main.getPrefix() + error));
        }
    }

    public FileConfiguration get() {
        return conf;
    }

    public void save() {
        try {
            conf.save(file);
        } catch (IOException e) {
            main.getConsole().sendMessage(main.messageFormat(main.getPrefix() + error));
        }
    }

    private void defaultConfig() {
        conf.set("Prefix", "&d&lGummy&f&lMC &r");
        conf.set("NoPerm", "&d&lGUMMY&f&lMC &7Comando sconosciuto.");
        //conf.set("Command-not-found", "{prefix}&7Utilizza &d/marry help");
        conf.set("Player-not-found", "{prefix}&7Player non trovato!");
        conf.set("Command-only-player", "{prefix}&7Solo i player possono eseguire questo comando!");
        conf.set("Message-error", "{prefix}&cC'è un errore con il comando! Contatta un admin il prima possibile!");

        conf.set("GummyMC.Command.Message.Usage", "{prefix}&7Utilizza il comando &o/message <Player> <Message>&7.");
        conf.set("GummyMC.Command.Message.No-himself", "{prefix}&7Non puoi scrivere un messaggio privato a te stesso.");
        conf.set("GummyMC.Command.Message.Receiver-format", "&d&l(&7Da {playerName}&d&l) &7{message}");
        conf.set("GummyMC.Command.Message.Sender-format", "&d&l(&7A {playerName}&d&l) &7{message}");

        conf.set("GummyMC.Command.Reply.Usage", "{prefix}&7Utilizza il comando &o/reply <Message>&7.");
        conf.set("GummyMC.Command.Reply.NoReply", "{prefix}&7Non devi rispondere a nessuno.");

        conf.set("GummyMC.Command.Gamemode.Usage", "{prefix}&7Utilizza il comando &o/gm <gamemode>&7.");
        conf.set("GummyMC.Command.Gamemode.Success", "{prefix}&7Ora sei in &e{gamemode}&7.");
        conf.set("GummyMC.Command.Gamemode.Success-other", "{prefix}&7Il player è ora in &e{gamemode} &7({playerName}).");

        conf.set("GummyMC.Command.Vanish.Usage", "{prefix}&7Utilizza il comando &o/vanish&7.");
        conf.set("GummyMC.Command.Vanish.Success", "{prefix}&7Ora sei in &e{gamemode}&7.");
        conf.set("GummyMC.Command.Vanish.Success-other", "{prefix}&7Il player è ora in &e{gamemode} &7({playerName}).");
    }
}
