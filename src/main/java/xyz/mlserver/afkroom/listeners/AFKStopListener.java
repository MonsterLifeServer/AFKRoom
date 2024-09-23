package xyz.mlserver.afkroom.listeners;

import net.lapismc.afkplus.api.AFKStopEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.mlserver.afkroom.utils.LocationYmlAPI;

import java.util.UUID;

public class AFKStopListener implements Listener {

    @EventHandler
    public void onAFKStop(AFKStopEvent e) {
        Player player = Bukkit.getPlayer(e.getPlayer().getUUID());
        UUID uuid = player.getUniqueId();
        Location finalLocation;
        if (LocationYmlAPI.getFinalLocationMap().containsKey(uuid)) {
            finalLocation = LocationYmlAPI.getFinalLocationMap().get(uuid).clone();
            LocationYmlAPI.getFinalLocationMap().remove(uuid);
        } else {
            finalLocation = Bukkit.getWorlds().get(0).getSpawnLocation().clone();
        }
        player.teleport(finalLocation);
        LocationYmlAPI.saveLocation();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (LocationYmlAPI.getFinalLocationMap().containsKey(uuid)) {
            Location finalLocation = LocationYmlAPI.getFinalLocationMap().get(uuid).clone();
            LocationYmlAPI.getFinalLocationMap().remove(uuid);
            player.teleport(finalLocation);
            LocationYmlAPI.saveLocation();
        }
    }

}
