package xyz.hynse.hymotdstatus;

import org.bukkit.Bukkit;
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
            long time = world.getTime() / 1000;
            boolean isStormy = world.hasStorm();

            long hours = time / 3600;
            long minutes = (time % 3600) / 60;

            String timeicon = (hours >= 6 && hours < 18) ? "\u2600" : "\u1F319";
            String weathericon = isStormy ? "\u26C8" : (world.isClearWeather() ? "\u2600" : "\u1F327");
            String line2color = String.valueOf(net.md_5.bungee.api.ChatColor.of("#ffffff"));

            String timeString = String.format("%02d:%02d", hours, minutes);
            String customMOTDLine = line2color + timeicon + " Day: " + dayCount + " - Time: " + timeString + " " + weathericon;

            motdLines[1] = customMOTDLine;

            motd = String.join("\n", motdLines);

            event.setMotd(motd);
        }
    }
}
