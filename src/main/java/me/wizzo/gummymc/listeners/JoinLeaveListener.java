package me.wizzo.gummymc.listeners;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;

public class JoinLeaveListener implements Listener {

    private final GummyMC main;

    public JoinLeaveListener(GummyMC main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            //ADDARE NEL COSO DELLA FLY
        }

        if (!main.getDbGetter().alreadyIntoDatabase(player.getUniqueId())) {
            main.getDbCreater().createPlayer(player);
        }

        if (main.getDbGetter().isVanished(player.getUniqueId())) {
            for (Player online: main.getServer().getOnlinePlayers()) {
                online.hidePlayer(player);
            }
        }


        if (main.getServer().getOnlinePlayers().size() > 1) {
            for (Player online: main.getServer().getOnlinePlayers()) {
                for (String a: main.getDbGetter().getPlayerVanishedList()) {
                    player.hidePlayer(online);
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        /*if (Coso della fly contiene il player) {
            togli il player e gli metti setAllowFlight(false);
            }
         */
    }
}
