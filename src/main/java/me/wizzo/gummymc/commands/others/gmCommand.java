package me.wizzo.gummymc.commands.others;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class gmCommand implements CommandExecutor {

    private final GummyMC main;
    private final String perms, adminPerms;
    private final Map<UUID, String> gmPlayers = new HashMap<>();

    public gmCommand(GummyMC main, String perms, String adminPerms) {
        this.main = main;
        this.perms = perms;
        this.adminPerms = adminPerms;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!main.havePerms(sender, perms) || !main.havePerms(sender, adminPerms)) {
            sender.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (args.length == 0 || args.length > 2) {
            sender.sendMessage(main.getConfig("GummyMC.Command.Gamemode.Usage"));
            return true;
        }

        String gamemodes = args[0].toLowerCase();

        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(main.getConfig("Command-only-player"));
                return true;
            }
            Player player = (Player) sender;

            if (!main.havePerms(player, perms)) {
                sender.sendMessage(main.getConfig("NoPerm"));
                return true;
            }

            switch (gamemodes) {
                case "creative":
                case "c":
                case "1":
                    if (main.havePerms(player, perms+".creative") || main.havePerms(player, perms+".*")) {
                        try {
                            gmMethod(player, GameMode.CREATIVE);
                        } catch (Exception e) {
                            player.sendMessage(main.getConfig("Message-error"));
                            e.printStackTrace();
                        }
                    }
                    break;

                case "survival":
                case "s":
                case "0":
                    if (main.havePerms(player, perms+".survival") || main.havePerms(player, perms+".*")) {
                        try {
                            gmMethod(player, GameMode.SURVIVAL);
                        } catch (Exception e) {
                            player.sendMessage(main.getConfig("Message-error"));
                            e.printStackTrace();
                        }
                    }
                    break;

                case "adventure":
                case "a":
                case "2":
                    if (main.havePerms(player, perms+".adventure") || main.havePerms(player, perms+".*")) {
                        try {
                            gmMethod(player, GameMode.ADVENTURE);
                        } catch (Exception e) {
                            player.sendMessage(main.getConfig("Message-error"));
                            e.printStackTrace();
                        }
                    }
                    break;

                case "spectator":
                case "sp":
                case "3":
                    if (main.havePerms(player, perms+".spectator") || main.havePerms(player, perms+".*")) {
                        try {
                            gmMethod(player, GameMode.SPECTATOR);
                        } catch (Exception e) {
                            player.sendMessage(main.getConfig("Message-error"));
                            e.printStackTrace();
                        }
                    }
                    break;

                default:
                    sender.sendMessage(main.getConfig("GummyMC.Command.Gamemode.Usage"));
                    break;
            }
            return true;
        }

        Player target = main.getServer().getPlayerExact(args[1]);

        if (!main.havePerms(sender, adminPerms)) {
            sender.sendMessage(main.getConfig("NoPerm"));
            return true;
        }

        if (main.isOffline(target)) {
            sender.sendMessage(main.getConfig("Player-not-found"));
            return true;
        }

        switch (gamemodes) {
            case "creative":
            case "c":
            case "1":
                if (main.havePerms(sender, adminPerms+".creative") || main.havePerms(sender, adminPerms+".*")) {
                    try {
                        gmMethod(target, GameMode.CREATIVE);
                        sender.sendMessage(main.getConfig("GummyMC.Command.Gamemode.Success-other")
                                .replace("{gamemode}", GameMode.CREATIVE.name())
                                .replace("{playerName}", target.getName())
                        );
                    } catch (Exception e) {
                        sender.sendMessage(main.getConfig("Message-error"));
                        e.printStackTrace();
                    }
                }
                break;

            case "survival":
            case "s":
            case "0":
                if (main.havePerms(sender, adminPerms+".survival") || main.havePerms(sender, adminPerms+".*")) {
                    try {
                        gmMethod(target, GameMode.SURVIVAL);
                        sender.sendMessage(main.getConfig("GummyMC.Command.Gamemode.Success-other")
                                .replace("{gamemode}", GameMode.SURVIVAL.name())
                                .replace("{playerName}", target.getName())
                        );
                    } catch (Exception e) {
                        sender.sendMessage(main.getConfig("Message-error"));
                        e.printStackTrace();
                    }
                }
                break;

            case "adventure":
            case "a":
            case "2":
                if (main.havePerms(sender, adminPerms+".adventure") || main.havePerms(sender, adminPerms+".*")) {
                    try {
                        gmMethod(target, GameMode.ADVENTURE);
                        sender.sendMessage(main.getConfig("GummyMC.Command.Gamemode.Success-other")
                                .replace("{gamemode}", GameMode.ADVENTURE.name())
                                .replace("{playerName}", target.getName())
                        );
                    } catch (Exception e) {
                        sender.sendMessage(main.getConfig("Message-error"));
                        e.printStackTrace();
                    }
                }
                break;

            case "spectator":
            case "sp":
            case "3":
                if (main.havePerms(sender, adminPerms+".spectator") || main.havePerms(sender, adminPerms+".*")) {
                    try {
                        gmMethod(target, GameMode.SPECTATOR);
                        sender.sendMessage(main.getConfig("GummyMC.Command.Gamemode.Success-other")
                                .replace("{gamemode}", GameMode.SPECTATOR.name())
                                .replace("{playerName}", target.getName())
                        );

                    } catch (Exception e) {
                        sender.sendMessage(main.getConfig("Message-error"));
                        e.printStackTrace();
                    }
                }
                break;

            default:
                sender.sendMessage(main.getConfig("GummyMC.Command.Gamemode.Usage"));
                break;
        }
        return true;
    }

    private void gmMethod(Player player, GameMode gamemode) {
        if (!gmPlayers.containsKey(player.getUniqueId())) {
            gmPlayers.put(player.getUniqueId(), gamemode.name());
        } else {
            gmPlayers.replace(player.getUniqueId(), gamemode.name());
        }
        player.setGameMode(gamemode);
        player.sendMessage(main.getConfig("GummyMC.Command.Gamemode.Success")
                .replace("{gamemode}", gamemode.name())
        );
    }
}
