package me.wizzo.gummymc.commands.warpSystem;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class WarpMainCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms;
    private final String globalPerms;
    protected WarpCreateCommand warpCreateCommand;
    protected WarpDeleteCommand warpDeleteCommand;
    protected WarpListCommand warpListCommand;
    protected WarpTeleportCommand warpTeleportCommand;

    public WarpMainCommand(GummyMC main, String perms) {
        this.main = main;
        this.perms = perms;
        this.globalPerms = perms + ".*";
        this.warpCreateCommand = new WarpCreateCommand(main, perms + ".create", globalPerms);
        this.warpDeleteCommand = new WarpDeleteCommand(main, perms + ".delete", globalPerms);
        this.warpListCommand = new WarpListCommand(main, perms + ".list", globalPerms);
        this.warpTeleportCommand = new WarpTeleportCommand(main, perms + ".teleport", globalPerms);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!main.havePerms(sender, perms) || !main.havePerms(sender, globalPerms)) {
            sender.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Warp.Usage"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
            case "set":
                this.warpCreateCommand.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "delete":
                this.warpDeleteCommand.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "list":
                this.warpListCommand.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "teleport":
            case "tp":
            case "go":
                this.warpTeleportCommand.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
                break;
            default:
                sender.sendMessage(main.getConfig("GummyMC.Command.Warp.Usage"));
                break;
        }
        return true;
    }
}
