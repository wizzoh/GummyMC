package me.wizzo.gummymc.listeners;

import me.wizzo.gummymc.GummyMC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    private final GummyMC main;

    public InventoryListener(GummyMC main) {
        this.main = main;
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getView().getTitle().contains("Warps")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        HumanEntity humanEntity = event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (event.getView().getTitle().contains("Warps")) {
            if ((item == null) || item.getType().equals(Material.AIR)) {
                return;
            }
            event.setCancelled(true);
            String clickedItem = item.getItemMeta().getDisplayName();

            if (clickedItem.equalsIgnoreCase("avanti") && item.getType().equals(Material.ARROW)) {
                humanEntity.sendMessage("§cAh? Quale avanti?");
            }
            String itemNameFormatted = ChatColor.stripColor(clickedItem.toLowerCase());

            if (humanEntity instanceof Player) {
                System.out.println("cliccato");
                main.getDbGetter().warpTeleport((Player) humanEntity, itemNameFormatted);
            }
        }
    }
}
