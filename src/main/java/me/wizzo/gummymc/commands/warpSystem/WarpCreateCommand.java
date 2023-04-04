package me.wizzo.gummymc.commands.warpSystem;

import me.wizzo.gummymc.GummyMC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCreateCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms;
    private final String globalPerms;

    public WarpCreateCommand(GummyMC main, String perms, String globalPerms) {
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

        if (args.length > 2) {
            player.sendMessage(main.getConfig("GummyMC.Command.Warp.Create.Usage"));
            return true;
        }

        String warpName = main.messageFormat(args[0]);
        String warpNameFormatted = ChatColor.stripColor(warpName.toLowerCase());
        if (main.getDbGetter().warpExists(warpNameFormatted)) {
            player.sendMessage(main.getConfig("GummyMC.Command.Warp.Create.Already-exist"));
            return true;
        }
        Location location = player.getLocation();

        if (args.length == 1) {
            try {
                main.getDbSetter().createWarps(
                        warpName,
                        warpNameFormatted,
                        location.getWorld().getName(),
                        String.valueOf(location.getX()),
                        String.valueOf(location.getY()),
                        String.valueOf(location.getZ()),
                        String.valueOf(location.getYaw()),
                        String.valueOf(location.getPitch()),
                        player.getName()
                );
                player.sendMessage(main.getConfig("GummyMC.Command.Warp.Create.Success")
                        .replace("{warpName}", warpName)
                );
            } catch (Exception e) {
                player.sendMessage(main.getConfig("Message-error"));
                e.printStackTrace();
            }
            return true;
        }

        if (!main.havePerms(player, perms + ".admin") || !main.havePerms(player, globalPerms)) {
            player.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        String target = args[1];
        try {
            main.getDbSetter().createWarps(
                    warpName,
                    warpNameFormatted,
                    location.getWorld().getName(),
                    String.valueOf(location.getX()),
                    String.valueOf(location.getY()),
                    String.valueOf(location.getZ()),
                    String.valueOf(location.getYaw()),
                    String.valueOf(location.getPitch()),
                    target
            );
            player.sendMessage(main.getConfig("GummyMC.Command.Warp.Create.Success")
                    .replace("{warpName}", warpName)
            );
        } catch (Exception e) {
            player.sendMessage(main.getConfig("Message-error"));
            e.printStackTrace();
        }
        return true;
    }
}
