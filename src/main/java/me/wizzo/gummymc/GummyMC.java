package me.wizzo.gummymc;

import me.wizzo.gummymc.commands.*;
import me.wizzo.gummymc.commands.privateMessage.messageCommand;
import me.wizzo.gummymc.commands.privateMessage.replyCommand;
import me.wizzo.gummymc.database.*;
import me.wizzo.gummymc.files.ConfigFile;
import me.wizzo.gummymc.files.DatabaseFile;
import me.wizzo.gummymc.listeners.AsyncChatListener;
import me.wizzo.gummymc.listeners.JoinLeaveListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class GummyMC extends JavaPlugin {

    private final ConsoleCommandSender console = getServer().getConsoleSender();
    private String prefix;
    private ConfigFile configFile;
    private DatabaseFile databaseFile;
    private HikariCPSettings hikariCPSettings;
    private Creater dbCreater;
    private Getter dbGetter;
    private Setter dbSetter;
    private final Map<String, String> lastMessageReceived = new HashMap<>();

    @Override
    public void onEnable() {
        createFolder();
        commands();
        listeners();
        files();
        databases();

        prefix = configFile.get().getString("Prefix");

        try {
            hikariCPSettings.initSource();
            console.sendMessage("");
            console.sendMessage(messageFormat(prefix + "Plugin online!"));
            console.sendMessage(messageFormat(prefix + "Created by wizzo <3!"));
            console.sendMessage("");
        } catch (Exception e) {
            console.sendMessage(messageFormat(prefix + "Errore durante il caricamento del plugin!!"));
            console.sendMessage(messageFormat(prefix + "Spegnimento in corso..."));
            e.printStackTrace();
            getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        hikariCPSettings.close(hikariCPSettings.getSource());
        console.sendMessage("");
        console.sendMessage(messageFormat(prefix + "Plugin offline!"));
        console.sendMessage(messageFormat(prefix + "Created by wizzo <3!"));
        console.sendMessage("");
    }

//---------------------------- PRIVATE -----------------------------//

    private void commands() {
        getCommand("message").setExecutor(new messageCommand(this, "gummymc.command.message"));
        getCommand("reply").setExecutor(new replyCommand(this, "gummymc.command.reply"));
    }

    private void listeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinLeaveListener(this), this);
        pm.registerEvents(new AsyncChatListener(), this);
    }

    private void files() {
        this.configFile = new ConfigFile(this);
        configFile.setup(this, "config.yml");

        this.databaseFile = new DatabaseFile(this);
        databaseFile.setup(this, "database.yml");
    }

    private void databases() {
        hikariCPSettings = new HikariCPSettings(this);
        this.dbCreater = new Creater(this);
        this.dbGetter = new Getter(this);
        this.dbSetter = new Setter(this);
    }

    private void createFolder() {
        if (!getDataFolder().exists()) {
            boolean success = getDataFolder().mkdirs();
            if (!success) {
                System.out.println(ChatColor.RED + "[GummyMC] ERRORE NEL CREARE LA CARTELLA, PLUGIN DISABILITATO");
                getPluginLoader().disablePlugin(this);
            }
        }
    }

//---------------------------- PUBLIC -----------------------------//

    public String messageFormat(String message) {
        return message.replace("{prefix}", prefix).replaceAll("&", "§");
    }
    public ConsoleCommandSender getConsole() {
        return console;
    }
    public String getPrefix() {
        return prefix;
    }
    public boolean havePerms(CommandSender sender, String perms) {
        return sender.hasPermission(perms) || sender.isOp();
    }
    public boolean isOnline(Player player) {
        return getServer().getOnlinePlayers().contains(player);
    }
    public String getConfig(String string) {
        return messageFormat(configFile.get().getString(string));
    }
    public String getDbConfig(String string) {
        return databaseFile.get().getString(string);
    }

    public HikariCPSettings getHikariCPSettings() {
        return hikariCPSettings;
    }
    public Creater getDbCreater() {
        return dbCreater;
    }
    public Getter getDbGetter() {
        return dbGetter;
    }
    public Setter getDbSetter() {
        return dbSetter;
    }
    public Map<String, String> getLastMessageReceived() {
        return lastMessageReceived;
    }
}
