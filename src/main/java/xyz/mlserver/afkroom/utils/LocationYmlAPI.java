package xyz.mlserver.afkroom.utils;

import org.bukkit.Location;
import xyz.mlserver.afkroom.AFKRoom;

import java.util.HashMap;
import java.util.UUID;

public class LocationYmlAPI {

    private static Location location;

    public static void setLocation(Location loc) {
        location = loc;
    }

    public static Location getLocation() {
        return location;
    }

    private static HashMap<UUID, Location> finalLocationMap;

    public static HashMap<UUID, Location> getFinalLocationMap() {
        if (finalLocationMap == null) {
            finalLocationMap = new HashMap<>();
        }
        return finalLocationMap;
    }

    public static void setFinalLocationMap(HashMap<UUID, Location> map) {
        finalLocationMap = map;
    }

    public static void saveLocation() {
        AFKRoom.getConfiguration().setLocation("afkLocation", location);
        for (UUID uuid : finalLocationMap.keySet()) {
            AFKRoom.getConfiguration().getConfig().set("finalLocation", null);
            AFKRoom.getConfiguration().setLocation("finalLocation." + uuid.toString(), finalLocationMap.get(uuid));
        }
        AFKRoom.getConfiguration().saveConfig();
    }

    public static void loadLocation() {
        location = AFKRoom.getConfiguration().getLocation("afkLocation", null);
        finalLocationMap = new HashMap<>();
        if (AFKRoom.getConfiguration().getConfig().getConfigurationSection("finalLocation") == null) {
            return;
        }
        for (String key : AFKRoom.getConfiguration().getConfig().getConfigurationSection("finalLocation").getKeys(false)) {
            finalLocationMap.put(UUID.fromString(key), AFKRoom.getConfiguration().getLocation("finalLocation." + key));
        }
    }

}
