package me.wizzo.gummymc.commands.warpSystem;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
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
        String warpName = args[0].toLowerCase();
        String startedName = "Warps." + warpName;

        if (main.getWarpsConfig().get(startedName) == null) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Warp.Teleport.Not-exist"));
            return true;
        }

        try {
            Location location;
            String name = main.getWarpsConfig().getString(startedName + ".Name");
            String world = main.getWarpsConfig().getString(startedName + ".World");
            double x = Double.parseDouble(main.getWarpsConfig().getString(startedName + ".X"));
            double y = Double.parseDouble(main.getWarpsConfig().getString(startedName + ".Y"));
            double z = Double.parseDouble(main.getWarpsConfig().getString(startedName + ".Z"));
            float yaw = Float.parseFloat(main.getWarpsConfig().getString(startedName + ".Yaw"));
            float pitch = Float.parseFloat(main.getWarpsConfig().getString(startedName + ".Pitch"));
            String authors = main.getWarpsConfig().getString(startedName + ".Membri");
            location = new Location(main.getServer().getWorld(world), x, y, z, yaw, pitch);

            if (main.getServer().getWorlds().contains(main.getServer().getWorld(world))) {
                player.teleport(location);
                player.sendMessage(main.getConfig("GummyMC.Command.Warp.Teleport.Success")
                        .replace("{warpName}", name)
                        .replace("{warpAuthors}", authors)
                );
            } else {
                player.sendMessage(main.getConfig("GummyMC.Command.Warp.Teleport.World-not-found")
                        .replace("{worldName}", world)
                );
            }
        } catch (NumberFormatException e) {
            player.sendMessage(main.getConfig("Message-error"));
            e.printStackTrace();
        }
        return true;
    }
}
