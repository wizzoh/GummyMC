package me.wizzo.gummymc.commands.others;

import me.wizzo.gummymc.GummyMC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms;

    public GodCommand(GummyMC main, String perms) {
        this.main = main;
        this.perms = perms;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(main.getConfig("Command-only-player"));
            return true;
        }
        Player player = (Player) sender;

        if (!main.havePerms(player, perms)) {
            player.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (args.length > 0) {
            player.sendMessage(main.getConfig("GummyMC.Command.God.Usage"));
            return true;
        }

        try {
            godMethod(player);
        } catch (Exception e) {
            player.sendMessage(main.getConfig("Message-error"));
            e.printStackTrace();
        }
        return true;
    }

    private void godMethod(Player player) {
        if (main.getGodPlayers().contains(player.getUniqueId())) {
            main.getGodPlayers().remove(player.getUniqueId());
            player.sendMessage(main.getConfig("GummyMC.Command.God.Disable"));
            main.getConsole().sendMessage(ChatColor.RED + player.getName() + " ha disattivato la god mode");
        } else {
            main.getGodPlayers().add(player.getUniqueId());
            player.sendMessage(main.getConfig("GummyMC.Command.God.Enable"));
            main.getConsole().sendMessage(ChatColor.RED + player.getName() + " ha attivato la gode mode");
        }
    }
}
