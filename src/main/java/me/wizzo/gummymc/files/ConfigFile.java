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
        conf.set("Prefix", "&d&lGUMMY&f&lMC &r");
        conf.set("NoPerm", "{prefix}&7Comando sconosciuto.");
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

        conf.set("GummyMC.Command.Mention.Usage", "{prefix}&7Utilizza il comando &o/mention&7.");
        conf.set("GummyMC.Command.Mention.Enable", "{prefix}&7Menzione &aabilitata&7! Da adesso i player potranno taggarti.");
        conf.set("GummyMC.Command.Mention.Disable", "{prefix}&7Menzione &cdisabilitata&7! Da adesso i player non potranno più taggarti.");

        conf.set("GummyMC.Command.God.Usage", "{prefix}&7Utilizza il comando &o/god&7.");
        conf.set("GummyMC.Command.God.Enable", "{prefix}&7God mode &aabilitata&7.");
        conf.set("GummyMC.Command.God.Disable", "{prefix}&7Gode mode &cdisabilitata&7.");

        conf.set("GummyMC.Command.Warp.Usage", "{prefix}&7Utilizza il comando &o/warp <create|delete|list|tp>&7.");

        conf.set("GummyMC.Command.Warp.Create.Usage", "{prefix}&7Utilizza il comando &o/warp create <warpName>&7.");
        conf.set("GummyMC.Command.Warp.Create.Already-exist", "{prefix}&7Esiste già un warp con questo nome!");
        conf.set("GummyMC.Command.Warp.Create.Success", "{prefix}&7Warp {warpName} &7creato con successo!");

        conf.set("GummyMC.Command.Warp.Delete.Usage", "{prefix}&7Utilizza il comando &o/warp delete <warpName>&7.");
        conf.set("GummyMC.Command.Warp.Delete.Not-exist", "{prefix}&7Non è stato trovato un warp con questo nome!");
        conf.set("GummyMC.Command.Warp.Delete.Success", "{prefix}&7Il warp {warpName} &7è stato eliminato!");

        conf.set("GummyMC.Command.Warp.Teleport.Usage", "{prefix}&7Utilizza il comando &o/warp teleport <warpName>&7.");
        conf.set("GummyMC.Command.Warp.Teleport.Not-exist", "{prefix}&7Non è stato trovato un warp con questo nome!");
        conf.set("GummyMC.Command.Warp.Teleport.Success", "{prefix}&7Ti sei teletrasportato al warp {warpName} &7(Membri: {warpAuthors}).");
        conf.set("GummyMC.Command.Warp.Teleport.World-not-found", "{prefix}&7Il mondo in cui si trova il warp non esiste! (worldName: {worldName})");

        conf.set("GummyMC.Command.Warp.List.Usage", "{prefix}&7Utilizza il comando &o/warp list &7oppure &o/warps&7.");
        /*conf.set("GummyMC.Command.Warp.List.Not-exist", "{prefix}&7Impossibile trovare i warp perchè il file è vuoto!");
        conf.set("GummyMC.Command.Warp.List.Success", "{prefix}&7Ecco a te la lista dei warps:\n{warpsList}\n");*/

        conf.set("GummyMC.Inventory.Warp.Personal-name", "&5Warps personali");
        conf.set("GummyMC.Inventory.Warp.All-name", "&5Warps totali");
        conf.set("GummyMC.Inventory.Warp.Others-name", "&5Warps di {playerName}");

        conf.set("GummyMC.Event.Chat.Sostituzione-cuore.Enable", "true");
        conf.set("GummyMC.Event.Chat.Sostituzione-cuore.Sostituire", "<3");
        conf.set("GummyMC.Event.Chat.Sostituzione-cuore.Sostituto", "&c❤&r");

        conf.set("GummyMC.Event.Chat.Menzione.Enable", "true");
        conf.set("GummyMC.Event.Chat.Menzione.Format", "&d@{playerName}&r");
    }
}
