package me.wizzo.gummymc.commands.others;

import me.wizzo.gummymc.GummyMC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms, adminPerms;

    public FlyCommand(GummyMC main, String perms, String adminPerms) {
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
            sender.sendMessage(main.getConfig("GummyMC.Command.Fly.Usage"));
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
                flyMethod(player);
            } catch (Exception e) {
                player.sendMessage(main.getConfig("Message-error"));
                e.printStackTrace();
            }
            return true;
        }
        Player target = main.getServer().getPlayerExact(args[0]);

        if (!main.havePerms(sender, adminPerms)) {
            sender.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (main.isOffline(target)) {
            sender.sendMessage(main.getConfig("Player-not-found"));
            return true;
        }

        try {
            flyMethod(target);
            if (main.getFlyPlayers().contains(target.getUniqueId())) {
                sender.sendMessage(main.getConfig("GummyMC.Command.Fly.Enable-other")
                        .replace("{playerName}", target.getName())
                );
                main.getConsole().sendMessage(ChatColor.RED + sender.getName() + " ha attivato la fly al player" + target.getName());
            } else {
                sender.sendMessage(main.getConfig("GummyMC.Command.Fly.Disable-other")
                        .replace("{playerName}", target.getName())
                );
                main.getConsole().sendMessage(ChatColor.RED + sender.getName() + " ha disattivato la fly al player" + target.getName());
            }
        } catch (Exception e) {
            sender.sendMessage(main.getConfig("Message-error"));
            e.printStackTrace();
        }
        return true;
    }

    private void flyMethod(Player player) {
        if (main.getFlyPlayers().contains(player.getUniqueId())) {
            main.getFlyPlayers().remove(player.getUniqueId());
            player.setAllowFlight(false);
            player.sendMessage(main.getConfig("GummyMC.Command.Fly.Disable"));
            main.getConsole().sendMessage(ChatColor.RED + player.getName() + " ha disattivato la fly");
        } else {
            main.getFlyPlayers().add(player.getUniqueId());
            player.setAllowFlight(true);
            player.sendMessage(main.getConfig("GummyMC.Command.Fly.Enable"));
            main.getConsole().sendMessage(ChatColor.RED + player.getName() + " ha attivato la fly");
        }
    }
}
