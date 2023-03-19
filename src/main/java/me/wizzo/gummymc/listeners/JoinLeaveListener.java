package me.wizzo.gummymc.listeners;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    private final GummyMC main;

    public JoinLeaveListener(GummyMC main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!main.getDbGetter().alreadyIntoDatabase(player.getUniqueId())) {
            main.getDbCreater().createPlayer(player);
        }

        if (main.getDbGetter().isVanished(player.getUniqueId())) {
            for (Player online: main.getServer().getOnlinePlayers()) {
                online.hidePlayer(player);
            }
        }

        if (main.getServer().getOnlinePlayers().size() > 1) {
            for (Player ignored : main.getServer().getOnlinePlayers()) {
                for (String a: main.getDbGetter().getPlayerVanishedList()) {
                    Player b = Bukkit.getPlayerExact(a);
                    player.hidePlayer(b);
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (main.getFlyPlayers().contains(player.getUniqueId())) {
            main.getFlyPlayers().remove(player.getUniqueId());
            player.setFlying(false);
        }
        main.getLastMessageReceived().remove(player.getName());
    }
}
