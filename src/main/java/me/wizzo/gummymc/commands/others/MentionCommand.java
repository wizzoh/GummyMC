package me.wizzo.gummymc.commands.others;

import me.wizzo.gummymc.GummyMC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MentionCommand implements CommandExecutor {
    private final GummyMC main;
    private final String perms;

    public MentionCommand(GummyMC main, String perms) {
        this.main = main;
        this.perms = perms;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(main.getConfig("Command-only-player"));
            return true;
        }
        Player player = (Player) sender;

        if (!main.havePerms(player, perms)) {
            player.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (args.length > 0) {
            player.sendMessage(main.getConfig("GummyMC.Command.Mention.Usage"));
            return true;
        }

        try {
            mentionMethod(player);
        } catch (Exception e) {
            player.sendMessage(main.getConfig("Message-error"));
            e.printStackTrace();
        }
        return true;
    }

    private void mentionMethod(Player player) {
        if (main.getDbGetter().isNotIntoMentionTables(player.getUniqueId())) {
            main.getDbCreater().createPlayerMention(player);
        }
        main.getDbSetter().mentionOnOff(player.getUniqueId());
        if (main.getDbGetter().haveMentionEnable(player.getUniqueId())) {
            player.sendMessage(main.getConfig("GummyMC.Command.Mention.Enable"));
            main.getConsole().sendMessage(ChatColor.RED + player.getName() + " ha attivato le menzioni");
        } else {
            player.sendMessage(main.getConfig("GummyMC.Command.Mention.Disable"));
            main.getConsole().sendMessage(ChatColor.RED + player.getName() + " ha disattivato le menzioni");
        }
    }
}
