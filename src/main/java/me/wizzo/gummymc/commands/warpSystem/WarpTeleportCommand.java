package me.wizzo.gummymc.commands.warpSystem;

import me.wizzo.gummymc.GummyMC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpTeleportCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms;
    private final String globalPerms;

    public WarpTeleportCommand(GummyMC main, String perms, String globalPerms) {
        this.main = main;
        this.perms = perms;
        this.globalPerms = globalPerms;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(main.getConfig("Command-only-player"));
            return true;
        }
        Player player = (Player) sender;

        if (!main.havePerms(player, perms) || !main.havePerms(player, globalPerms)) {
            player.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(main.getConfig("GummyMC.Command.Warp.Teleport.Usage"));
            return true;
        }
        String warpNameFormatted = ChatColor.stripColor(args[0].toLowerCase());

        if (!main.getDbGetter().warpExists(warpNameFormatted)) {
            player.sendMessage(main.getConfig("GummyMC.Command.Warp.Teleport.Not-exist"));
            return true;
        }

        try {
            main.getDbGetter().warpTeleport(player, warpNameFormatted);
        } catch (Exception e) {
            player.sendMessage(main.getConfig("Message-error"));
            e.printStackTrace();
        }
        return true;
    }
}
