package me.wizzo.gummymc.commands.warpSystem;

import me.wizzo.gummymc.GummyMC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WarpDeleteCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms;
    private final String globalPerms;

    public WarpDeleteCommand(GummyMC main, String perms, String globalPerms) {
        this.main = main;
        this.perms = perms;
        this.globalPerms = globalPerms;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!main.havePerms(sender, perms) || !main.havePerms(sender, globalPerms)) {
            sender.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Warp.Delete.Usage"));
            return true;
        }
        String warpNameFormatted = ChatColor.stripColor(args[0].toLowerCase());
        String startedName = "Warps." + warpNameFormatted;
        String warpName = main.getWarpsConfig().getString(startedName + ".Name");

        if (main.getWarpsConfig().get(startedName) == null) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Warp.Delete.Not-exist"));
            return true;
        }

        try {
            main.getWarpsConfig().set(startedName, null);
            main.saveWarpsConfig();

            sender.sendMessage(main.getConfig("GummyMC.Command.Warp.Delete.Success")
                    .replace("{warpName}", warpName)
            );
        } catch (Exception e) {
            sender.sendMessage(main.getConfig("Message-error"));
            e.printStackTrace();
        }
        return true;
    }
}
