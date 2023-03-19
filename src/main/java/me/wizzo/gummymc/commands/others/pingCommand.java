package me.wizzo.gummymc.commands.others;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class pingCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms;
    private Method getHandleMethod;
    private Field pingField;

    public pingCommand(GummyMC main, String perms) {
        this.main = main;
        this.perms = perms;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!main.havePerms(sender, perms)) {
            sender.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (args.length > 1) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Ping.Usage"));
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(main.getConfig("Command-only-player"));
                return true;
            }
            Player player = (Player) sender;

            try {
                player.sendMessage(main.getConfig("GummyMC.Command.Ping.Own")
                        .replace("{ping}", String.valueOf(getPing(player)))
                );
            } catch (Exception e) {
                player.sendMessage(main.getConfig("Message-error"));
                e.printStackTrace();
            }
            return true;
        }
        Player target = main.getServer().getPlayerExact(args[0]);

        if (main.isOffline(target)) {
            sender.sendMessage(main.getConfig("Player-not-found"));
            return true;
        }

        try {
            sender.sendMessage(main.getConfig("GummyMC.Command.Ping.Others")
                    .replace("{ping}", String.valueOf(getPing(target)))
                    .replace("{playerName}", target.getName())
            );
        } catch (Exception e) {
            sender.sendMessage(main.getConfig("Message-error"));
            e.printStackTrace();
        }
        return true;
    }

    private int getPing(Player player) throws Exception {

        if (getHandleMethod == null) {
            getHandleMethod = player.getClass().getDeclaredMethod("getHandle");
            getHandleMethod.setAccessible(true);
        }
        Object entityPlayer = getHandleMethod.invoke(player);
        if (pingField == null) {
            pingField = entityPlayer.getClass().getDeclaredField("ping");
            pingField.setAccessible(true);
        }
        return pingField.getInt(entityPlayer);
    }
}
