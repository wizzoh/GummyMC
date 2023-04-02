package me.wizzo.gummymc.commands.warpSystem;

import me.wizzo.gummymc.GummyMC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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

        if (args.length == 0) {
            try {
                Inventory inventory = main.getServer().createInventory(null, 45, main.getConfig("GummyMC.Inventory.Warp.Personal-name"));
                main.getDbGetter().getPlayerWarpsList(player);

                for (int a = 0; a < main.getWarpsName().size(); a++) {
                    String warps = main.getWarpsName().get(a);
                    String warpNameNoColor = ChatColor.stripColor(warps.toLowerCase());
                    addItemOnGui(
                            inventory,
                            a,
                            Material.WOOL,
                            6,
                            "§r" + main.getDbGetter().getWarpName(warpNameNoColor),
                            "§7" + main.getDbGetter().getWarpsMembers(warpNameNoColor)
                    );
                }
                player.openInventory(inventory);
                main.getWarpsName().clear();
            } catch (Exception e) {
                player.sendMessage(main.getConfig("Message-error"));
                e.printStackTrace();
            }
            return true;

        }

        if (args[0].equalsIgnoreCase("all")) {

            try {
                Inventory inventory = main.getServer().createInventory(null, 45, main.getConfig("GummyMC.Inventory.Warp.All-name"));
                main.getDbGetter().getAllWarpsList();

                for (int a = 0; a < main.getWarpsName().size(); a++) {
                    String warps = main.getWarpsName().get(a);
                    String warpNameNoColor = ChatColor.stripColor(warps.toLowerCase());
                    addItemOnGui(
                            inventory,
                            a,
                            Material.WOOL,
                            6,
                            "§r" + main.getDbGetter().getWarpName(warpNameNoColor),
                            "§7" + main.getDbGetter().getWarpsMembers(warpNameNoColor)
                    );
                }
                player.openInventory(inventory);
                main.getWarpsName().clear();
            } catch (Exception e) {
                player.sendMessage(main.getConfig("Message-error"));
                e.printStackTrace();
            }
            return true;
        }

        OfflinePlayer target = main.getServer().getOfflinePlayer(args[0]);
        try {
            Inventory inventory = main.getServer().createInventory(null, 45, main.getConfig("GummyMC.Inventory.Warp.Others-name")
                    .replace("{playerName}", target.getName())
            );
            main.getDbGetter().getPlayerWarpsList(target);

            for (int a = 0; a < main.getWarpsName().size(); a++) {
                String warps = main.getWarpsName().get(a);
                String warpNameNoColor = ChatColor.stripColor(warps.toLowerCase());
                addItemOnGui(
                        inventory,
                        a,
                        Material.WOOL,
                        6,
                        "§r" + main.getDbGetter().getWarpName(warpNameNoColor),
                        "§7" + main.getDbGetter().getWarpsMembers(warpNameNoColor)
                );
            }
            player.openInventory(inventory);
            main.getWarpsName().clear();
        } catch (Exception e) {
            player.sendMessage(main.getConfig("Message-error"));
            e.printStackTrace();
        }
        return true;
    }

    private void addItemOnGui(Inventory inventory, int slot, Material material, int id, String name, String lore) {
        ItemStack item = new ItemStack(material, 1, (short) id);
        ItemMeta meta = item.getItemMeta();
        List<String> loreList = new ArrayList<>();
        loreList.add(lore);
        meta.setDisplayName(name);
        meta.setLore(loreList);
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }
}
