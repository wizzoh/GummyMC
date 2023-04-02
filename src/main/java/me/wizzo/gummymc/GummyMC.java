package me.wizzo.gummymc;

import me.wizzo.gummymc.commands.others.*;
import me.wizzo.gummymc.commands.privateMessage.MessageCommand;
import me.wizzo.gummymc.commands.privateMessage.ReplyCommand;
import me.wizzo.gummymc.commands.teleportCommands.TpallCommand;
import me.wizzo.gummymc.commands.teleportCommands.TpCommand;
import me.wizzo.gummymc.commands.warpSystem.WarpListCommand;
import me.wizzo.gummymc.commands.warpSystem.WarpMainCommand;
import me.wizzo.gummymc.database.*;
import me.wizzo.gummymc.files.ConfigFile;
import me.wizzo.gummymc.files.DatabaseFile;
import me.wizzo.gummymc.listeners.AsyncChatListener;
import me.wizzo.gummymc.listeners.DamageListener;
import me.wizzo.gummymc.listeners.InventoryListener;
import me.wizzo.gummymc.listeners.JoinLeaveListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

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
    private final List<UUID> flyPlayers = new ArrayList<>();
    private final List<UUID> godPlayers = new ArrayList<>();
    private final List<String> warpsName = new ArrayList<>();

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
            dbCreater.createTables();
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
        getCommand("message").setExecutor(new MessageCommand(this, "gummymc.command.message"));
        getCommand("reply").setExecutor(new ReplyCommand(this, "gummymc.command.reply"));
        getCommand("gm").setExecutor(new GmCommand(this, "gummymc.command.gm", "gummymc.command.gm.others"));
        getCommand("vanish").setExecutor(new VanishCommand(this, "gummymc.command.vanish", "gummymc.command.vanish.others"));
        getCommand("fly").setExecutor(new FlyCommand(this, "gummymc.command.fly", "gummymc.command.fly.others"));
        getCommand("teleport").setExecutor(new TpCommand(this, "gummymc.command.teleport", "gummymc.command.teleport.others"));
        getCommand("teleportall").setExecutor(new TpallCommand(this, "gummymc.command.teleportall", "gummymc.command.teleportall.others"));
        getCommand("invsee").setExecutor(new InvseeCommand(this, "gummymc.command.invsee"));
        getCommand("ping").setExecutor(new PingCommand(this, "gummymc.command.ping"));
        getCommand("mention").setExecutor(new MentionCommand(this, "gummymc.command.mention"));
        getCommand("god").setExecutor(new GodCommand(this, "gummymc.command.god"));
        getCommand("warp").setExecutor(new WarpMainCommand(this, "gummymc.command.warp"));
        getCommand("warps").setExecutor(new WarpListCommand(this, "gummymc.command.warp.list", "gummymc.command.warp.*"));
    }

    private void listeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinLeaveListener(this), this);
        pm.registerEvents(new AsyncChatListener(this), this);
        pm.registerEvents(new DamageListener(this), this);
        pm.registerEvents(new InventoryListener(this), this);
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
    public boolean isOffline(Player player) {
        return !getServer().getOnlinePlayers().contains(player);
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
    public List<UUID> getFlyPlayers() {
        return flyPlayers;
    }
    public List<UUID> getGodPlayers() {
        return godPlayers;
    }
    public List<String> getWarpsName() {
        return warpsName;
    }
}
