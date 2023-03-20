package me.wizzo.gummymc.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.wizzo.gummymc.GummyMC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListener implements Listener {

    private final GummyMC main;

    public AsyncChatListener(GummyMC main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void chat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();

        if (Boolean.parseBoolean(main.getConfig("GummyMC.Event.Chat.Sostituzione-cuore.Enable"))) {
            if (message.contains(main.getConfig("GummyMC.Event.Chat.Sostituzione-cuore.Sostituire"))) {
                message = message.replace(main.getConfig("GummyMC.Event.Chat.Sostituzione-cuore.Sostituire"),
                        main.getConfig("GummyMC.Event.Chat.Sostituzione-cuore.Sostituto")
                );
                message = PlaceholderAPI.setPlaceholders(player, message);
            }
        }

        if (Boolean.parseBoolean(main.getConfig("GummyMC.Event.Chat.Menzione.Enable"))) {
            for (Player online: main.getServer().getOnlinePlayers()) {
                String name = online.getName();
                Player target = main.getServer().getPlayerExact(name);
                if (message.contains(name) && main.getDbGetter().haveMentionEnable(target.getUniqueId())) {
                    message = message.replace(name, main.getConfig("GummyMC.Event.Chat.Menzione.Format").replace("{playerName}", name));
                    message = PlaceholderAPI.setPlaceholders(player, message);
                }
            }
        }

        event.setMessage(message);
    }
}
