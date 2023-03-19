package me.wizzo.gummymc.commands.teleportCommands;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class tpCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms, adminPerms;

    public tpCommand(GummyMC main, String perms, String adminPerms) {
        this.main = main;
        this.perms = perms;
        this.adminPerms = adminPerms;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!main.havePerms(sender, perms) || !main.havePerms(sender, adminPerms)) {
            sender.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (args.length == 0 || args.length > 2) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Teleport.Usage"));
            return true;
        }

        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(main.getConfig("Command-only-player"));
                return true;
            }
            Player player = (Player) sender;

            if (!main.havePerms(player, perms)) {
                sender.sendMessage(main.getConfig("NoPerm"));
                return true;
            }
            Player target = main.getServer().getPlayerExact(args[0]);

            if (main.isOffline(target)) {
                sender.sendMessage(main.getConfig("Player-not-found"));
                return true;
            }

            try {
                player.teleport(target);
                player.sendMessage(main.getConfig("GummyMC.Command.Teleport.Success")
                        .replace("{playerName}", target.getName())
                );
            } catch (Exception e) {
                sender.sendMessage(main.getConfig("Message-error"));
                e.printStackTrace();
            }
            return true;
        }

        if (!main.havePerms(sender, adminPerms)) {
            sender.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        Player target = main.getServer().getPlayerExact(args[0]);
        Player target2 = main.getServer().getPlayerExact(args[1]);

        if (main.isOffline(target) || main.isOffline(target2)) {
            sender.sendMessage(main.getConfig("Player-not-found"));
            return true;
        }

        if (target.getName().equalsIgnoreCase(target2.getName())) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Teleport.No-himself"));
            return true;
        }

        try {
            target.teleport(target2);
            sender.sendMessage(main.getConfig("GummyMC.Command.Teleport.Success-other")
                    .replace("{playerName}", target.getName())
                    .replace("{targetName}", target2.getName())
            );
        } catch (Exception e) {
            sender.sendMessage(main.getConfig("Message-error"));
            e.printStackTrace();
        }
        return true;
    }
}
