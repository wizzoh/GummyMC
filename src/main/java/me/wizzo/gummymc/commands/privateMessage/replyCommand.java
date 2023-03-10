package me.wizzo.gummymc.commands.privateMessage;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class replyCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms;

    public replyCommand(GummyMC main, String perms) {
        this.main = main;
        this.perms = perms;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!main.havePerms(sender, perms)) {
            sender.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Reply.Usage"));
            return true;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }
        String message = builder.toString();

        String targetName = main.getLastMessageReceived().get(sender.getName());
        if (targetName == null) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Reply.NoReply"));
            return true;
        }
        Player target = main.getServer().getPlayerExact(targetName);

        if (!main.isOnline(target)) {
            sender.sendMessage(main.getConfig("Player-not-found"));
            return true;
        }

        try {
            target.sendMessage(main.getConfig("GummyMC.Command.Message.Receiver-format")
                    .replace("{playerName}", sender.getName())
                    .replace("{message}", message)
            );

            sender.sendMessage(main.getConfig("GummyMC.Command.Message.Sender-format")
                    .replace("{playerName}", target.getName())
                    .replace("{message}", message)
            );
            main.getLastMessageReceived().put(target.getName(), sender.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
