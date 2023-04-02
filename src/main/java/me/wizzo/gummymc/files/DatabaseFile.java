package me.wizzo.gummymc.files;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DatabaseFile {

    private File file;
    private FileConfiguration conf;
    private final GummyMC main;
    private final String error = "&cErrore con il caricamento del file";

    public DatabaseFile(GummyMC main) {
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
        conf.set("Url", "jdbc:mariadb://{ip}:{port}/{database}");
        conf.set("ClassName", "org.mariadb.jdbc.Driver");
        conf.set("Host", "127.0.0.1");
        conf.set("Port", 3306);
        conf.set("Database", "database");
        conf.set("Username", "root");
        conf.set("Password", "password");
        conf.set("Max-pool-size", 10);
        conf.set("Table.Vanish", "vanished_staff");
        conf.set("Table.Mention", "players_mention");
        conf.set("Table.WarpsList", "warpsList");
    }
}
