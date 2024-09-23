package xyz.mlserver.afkroom;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import xyz.mlserver.afkroom.utils.LocationYmlAPI;
import xyz.mlserver.java.sql.DataBase;
import xyz.mlserver.java.sql.mysql.MySQL;
import xyz.mlserver.java.sql.sqlite.SQLite;
import xyz.mlserver.mc.util.CustomConfiguration;

public final class AFKRoom extends JavaPlugin {

    private static JavaPlugin plugin;

    private static CustomConfiguration config;

    public static CustomConfiguration getConfiguration() {
        return config;
    }

    public static DataBase dataBase;
    public static String host, db, username, password;
    public static int port;

    public static boolean isMySQL = false;

    private static Team afkTeam;

    public static Team getAFKTeam() {
        return afkTeam;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        config = new CustomConfiguration(plugin);
        config.saveDefaultConfig();

        host = config.getConfig().getString("mysql.host");
        port = config.getConfig().getInt("mysql.port");
        db = config.getConfig().getString("mysql.database");
        username = config.getConfig().getString("mysql.username");
        password = config.getConfig().getString("mysql.password");

        isMySQL = config.getConfig().getBoolean("database", false);

        getCommand("afkroom").setExecutor(new xyz.mlserver.afkroom.cmds.AFKRoomCmd());

        getServer().getPluginManager().registerEvents(new xyz.mlserver.afkroom.listeners.AFKStartListener(), this);
        getServer().getPluginManager().registerEvents(new xyz.mlserver.afkroom.listeners.AFKStopListener(), this);

        LocationYmlAPI.loadLocation();

        Scoreboard scoreboard = getServer().getScoreboardManager().getNewScoreboard();
        if (scoreboard.getTeam("AFK") == null) {
            afkTeam = scoreboard.registerNewTeam("AFK");
        } else {
            afkTeam = scoreboard.getTeam("AFK");
        }
        afkTeam.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        LocationYmlAPI.saveLocation();
    }

    public static DataBase getDB() {
        if (dataBase == null) {
            try {
                if (isMySQL) {
                    dataBase = new DataBase(plugin, new MySQL(
                            host,
                            port,
                            db,
                            username,
                            password,
                            "useSSL=false&useLegacyDatetimeCode=false&autoReconnect=true"
                    ));
                } else {
                    dataBase = new DataBase(plugin, new SQLite("afkroom.db", plugin.getDataFolder().getAbsolutePath()));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return dataBase;
    }
}
