package xyz.hynse.hymotdstatus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hymotdstatus extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {

        String motd = event.getMotd();
        String[] motdLines = motd.split("\n");

        // Check if there are at least 2 lines in the MOTD
        if (motdLines.length >= 2) {
            World world = Bukkit.getWorlds().get(0);

            long dayCount = world.getFullTime() / 24000;
            long time = world.getTime();
            boolean isStormy = world.hasStorm();

            long hours = (time / 1000) % 24;
            long minutes = (time % 1000) * 60 / 1000;

            String timeicon = (hours >= 6 && hours < 18) ? "\u2600" : "\u263D";
            String thunderEmoji = "\u26C8"; // Thunder emoji
            String rainEmoji = "\uD83C\uDF27"; // Rain emoji
            String cloudEmoji = "\u2601"; // Cloud emoji
            String weathericon = isStormy ? thunderEmoji : (world.isClearWeather() ? cloudEmoji : rainEmoji);
            String line1color = String.valueOf(net.md_5.bungee.api.ChatColor.of("#ff0000")); // Change this color code
            String line2color = String.valueOf(net.md_5.bungee.api.ChatColor.of("#ffffff"));


            String timeString = String.format("%02d:%02d", hours, minutes);
            String formattedLine2 = line2color + timeicon + " Day: " + dayCount + " - " + weathericon + " Time: " + timeString;

            formattedLine2 = centerAlign(formattedLine2, 64);

            motdLines[1] = formattedLine2;

            motd = String.join("\n", motdLines);

            event.setMotd(motd);
        }
    }

    private String centerAlign(String text, int maxLength) {
        int totalSpaces = Math.max(0, maxLength - text.length());
        int leftSpaces = totalSpaces / 2;
        int rightSpaces = totalSpaces - leftSpaces;
        return " ".repeat(leftSpaces) + text + " ".repeat(rightSpaces);
    }
}
