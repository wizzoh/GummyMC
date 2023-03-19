package me.wizzo.gummymc.commands.teleportCommands;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tpAllCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms, adminPerms;

    public tpAllCommand(GummyMC main, String perms, String adminPerms) {
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

        if (args.length > 1) {
            sender.sendMessage(main.getConfig("GummyMC.Command.TpAll.Usage"));
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(main.getConfig("Command-only-player"));
                return true;
            }
            Player player = (Player) sender;

            if (!main.havePerms(player, perms)) {
                sender.sendMessage(main.getConfig("NoPerm"));
                return true;
            }

            try {
                for (Player online: main.getServer().getOnlinePlayers()) {
                    online.teleport(player);
                }
                player.sendMessage(main.getConfig("GummyMC.Command.TpAll.Success")
                        .replace("{playerName}", "te stesso")
                );
            } catch (Exception e) {
                sender.sendMessage(main.getConfig("Message-error"));
                e.printStackTrace();
            }
        }
        Player target = main.getServer().getPlayerExact(args[0]);

        if (main.isOffline(target)) {
            sender.sendMessage(main.getConfig("Player-not-found"));
            return true;
        }

        try {
            for (Player online: main.getServer().getOnlinePlayers()) {
                online.teleport(target);
            }
            sender.sendMessage(main.getConfig("GummyMC.Command.TpAll.Success")
                    .replace("{playerName}", target.getName())
            );
        } catch (Exception e) {
            sender.sendMessage(main.getConfig("Message-error"));
            e.printStackTrace();
        }
        return true;
    }
}
