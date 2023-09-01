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
        String[] motdLines = event.getMotd().split("\n");

        if (motdLines.length >= 2) {
            World world = Bukkit.getWorlds().get(0);

            long dayCount = world.getFullTime() / 24000;
            long time = world.getTime() / 1000;
            boolean isStormy = world.hasStorm();

            long hours = time / 3600;
            long minutes = (time % 3600) / 60;

            String timeString = String.format("%02d:%02d", hours, minutes);
            String weatherString = isStormy ? "Thunder" : (world.isClearWeather() ? "Clear" : "Rain");

            String customMOTDLine = "Day: " + dayCount + " - Time: " + timeString + " - Weather: " + weatherString;

            motdLines[1] = customMOTDLine;

            String updatedMOTD = String.join("\n", motdLines);

            event.setMotd(updatedMOTD);
        }
    }
}
