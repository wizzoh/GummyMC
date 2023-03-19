package me.wizzo.gummymc.commands.others;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class invseeCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms;

    public invseeCommand(GummyMC main, String perms) {
        this.main = main;
        this.perms = perms;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!main.havePerms(sender, perms)) {
            sender.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Invsee.Usage"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(main.getConfig("Command-only-player"));
            return true;
        }
        Player player = (Player) sender;
        Player target = main.getServer().getPlayerExact(args[0]);

        if (main.isOffline(target)) {
            player.sendMessage(main.getConfig("Player-not-found"));
            return true;
        }

        if (target.getName().equalsIgnoreCase(player.getName())) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Invsee.No-himself"));
            return true;
        }

        try {
            player.openInventory(target.getInventory());
            player.sendMessage(main.getConfig("GummyMC.Command.Invsee.Success")
                    .replace("{playerName}", target.getName())
            );
        } catch (Exception e) {
            player.sendMessage(main.getConfig("Message-error"));
            e.printStackTrace();
        }
        return true;
    }
}
