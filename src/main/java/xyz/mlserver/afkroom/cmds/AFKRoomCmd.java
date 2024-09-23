package xyz.mlserver.afkroom.cmds;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.mlserver.afkroom.utils.LocationYmlAPI;

public class AFKRoomCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }
        if (!sender.hasPermission("afkroom.setroom")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        Player player = (Player) sender;
        Location location = player.getLocation().clone();
        LocationYmlAPI.setLocation(location);
        sender.sendMessage(ChatColor.GREEN + "AFK Room location set.(X: " + String.format("%.1f", location.getX()) + ", Y: " + String.format("%.1f", location.getY()) + ", Z: " + String.format("%.1f", location.getZ()) + ", Yaw: " + String.format("%.1f", location.getYaw()) + ", Pitch: " + String.format("%.1f", location.getPitch()) + ")");
        return true;
    }
}
