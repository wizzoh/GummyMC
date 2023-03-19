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
        conf.set("GummyMC.Command.Vanish.Enable", "{prefix}&7Vanish &aabilitata&7.");
        conf.set("GummyMC.Command.Vanish.Disable", "{prefix}&7Vanish &cdisabilitata&7.");
        conf.set("GummyMC.Command.Vanish.Enable-other", "{prefix}&7Vanish &aabilitata&7 per {playerName}.");
        conf.set("GummyMC.Command.Vanish.Disable-other", "{prefix}&7Vanish &cdisabilitata&7 per {playerName}.");

        conf.set("GummyMC.Command.Fly.Usage", "{prefix}&7Utilizza il comando &o/fly&7.");
        conf.set("GummyMC.Command.Fly.Enable", "{prefix}&7Fly &aabilitata&7.");
        conf.set("GummyMC.Command.Fly.Disable", "{prefix}&7Fly &cdisabilitata&7.");
        conf.set("GummyMC.Command.Fly.Enable-other", "{prefix}&7Fly &aabilitata&7 per {playerName}.");
        conf.set("GummyMC.Command.Fly.Disable-other", "{prefix}&7Fly &cdisabilitata&7 per {playerName}.");

        conf.set("GummyMC.Command.Teleport.Usage", "{prefix}&7Utilizza il comando &o/tp <Player> [Target]&7.");
        conf.set("GummyMC.Command.Teleport.No-himself", "{prefix}&7Non puoi teletrasportare il player da se stesso.");
        conf.set("GummyMC.Command.Teleport.Success", "{prefix}&7Ti sei teletrasportato da &e{playerName}&7.");
        conf.set("GummyMC.Command.Teleport.Success-other", "{prefix}&7Hai teletrasportato il player &e{playerName} &7da &e{targetName}&7.");

        conf.set("GummyMC.Command.TpAll.Usage", "{prefix}&7Utilizza il comando &o/tpall [target]&7.");
        conf.set("GummyMC.Command.TpAll.Success", "{prefix}&7Hai teletrasportato tutti i player da &e{playerName}&7.");

        conf.set("GummyMC.Command.Invsee.Usage", "{prefix}&7Utilizza il comando &o/invsee <Player>&7.");
        conf.set("GummyMC.Command.Invsee.No-himself", "{prefix}&7Bro se vuoi aprire il tuo inventario perchè usare questo comando?");
        conf.set("GummyMC.Command.Invsee.Success", "{prefix}&7Hai aperto l'inventario di &e{playerName}&7.");

        conf.set("GummyMC.Command.Ping.Usage", "{prefix}&7Utilizza il comando &o/ping [player]&7.");
        conf.set("GummyMC.Command.Ping.Own", "{prefix}&7Il tuo ping è di &e{ping} &7ms.");
        conf.set("GummyMC.Command.Ping.Others", "{prefix}&7Il ping di {playerName} è di &e{ping} &7ms");
    }
}
