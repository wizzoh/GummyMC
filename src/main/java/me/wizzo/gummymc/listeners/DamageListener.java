package me.wizzo.gummymc.listeners;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    private final GummyMC main;

    public DamageListener(GummyMC main) {
        this.main = main;
    }


    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (main.getGodPlayers().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}
