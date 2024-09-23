package xyz.mlserver.afkroom.listeners;

import net.lapismc.afkplus.api.AFKStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.mlserver.afkroom.utils.LocationYmlAPI;

public class AFKStartListener implements Listener {

    @EventHandler
    public void onAFKStart(AFKStartEvent e) {
        Player player = Bukkit.getPlayer(e.getPlayer().getUUID());
        Location finalLocation = player.getLocation().clone();
        LocationYmlAPI.getFinalLocationMap().put(player.getUniqueId(), finalLocation);
        player.teleport(LocationYmlAPI.getLocation());
        LocationYmlAPI.saveLocation();
    }

}
