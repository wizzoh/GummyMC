package me.wizzo.gummymc.commands.warpSystem;

import me.wizzo.gummymc.GummyMC;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class WarpListCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms;
    private final String globalPerms;

    public WarpListCommand(GummyMC main, String perms, String globalPerms) {
        this.main = main;
        this.perms = perms;
        this.globalPerms = globalPerms;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!main.getWarpsConfig().contains("Warps.")) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Warp.List.Not-exist"));
            return true;
        }

        Set<String> warps = main.getWarpsConfig().getConfigurationSection("Warps.").getKeys(false);
        String[] warpList = warps.toArray(new String[warps.size()]);

        if (!(sender instanceof Player)) {
            sender.sendMessage(main.getConfig("Command-only-player"));
            return true;
        }
        Player player = (Player) sender;

        if (!main.havePerms(player, perms) || !main.havePerms(player, globalPerms)) {
            player.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (args.length > 1) {
            player.sendMessage(main.getConfig("GummyMC.Command.Warp.List.Usage"));
            return true;
        }

        if (main.getWarpsConfig() == null) {
            player.sendMessage(main.getConfig("GummyMC.Command.Warp.List.Not-exist"));
            return true;
        }

        if (args.length == 0) {
            try {
                player.sendMessage(main.getConfig("GummyMC.Command.Warp.List.Success"));
                for (String tmp: warpList) {
                    Set<String> warpss = main.getWarpsConfig().getConfigurationSection("Warps." + tmp + ".").getKeys(false);
                    //String[] warpList2 = warps.toArray(new String[warpss.size()]);
                    System.out.println(warpss);
                }
            } catch (Exception e) {
                player.sendMessage(main.getConfig("Message-error"));
                e.printStackTrace();
            }
        }

        if (args[0].equalsIgnoreCase("all")) {
            try {
                player.sendMessage(main.getConfig("GummyMC.Command.Warp.List.Success"));
                for (String tmp: warpList) {
                    TextComponent tmpObj = new TextComponent(warpList[warpList.length - 1].equals(tmp) ? tmp : tmp + ", ");
                    tmpObj.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warp tp " + tmp));
                    player.spigot().sendMessage(tmpObj);
                }
            } catch (Exception e) {
                player.sendMessage(main.getConfig("Message-error"));
                e.printStackTrace();
            }
            return true;
        }
        return true;
    }
}
