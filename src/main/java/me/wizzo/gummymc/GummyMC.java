package me.wizzo.gummymc;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class GummyMC extends JavaPlugin {

    private final ConsoleCommandSender console = getServer().getConsoleSender();
    private String prefix = "&dGummy&fMC ";
    @Override
    public void onEnable() {
        createFolder();
        commands();
        listeners();
        files();
        databases();

        try {
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
        console.sendMessage("");
        console.sendMessage(messageFormat(prefix + "Plugin offline!"));
        console.sendMessage(messageFormat(prefix + "Created by wizzo <3!"));
        console.sendMessage("");
    }

//---------------------------- PRIVATE -----------------------------//

    private void commands() {

    }

    private void listeners() {

    }

    private void files() {

    }

    private void databases() {

    }

    private void createFolder() {
        if (!getDataFolder().exists()) {
            boolean success = getDataFolder().mkdirs();
            if (!success) {
                getPluginLoader().disablePlugin(this);
                System.out.println(ChatColor.RED + "[GummyMC] ERRORE NEL CREARE LA CARTELLA, PLUGIN DISABILITATO");
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
}
