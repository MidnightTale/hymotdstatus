package xyz.hynse.hymotdstatus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
        World world = Bukkit.getWorlds().getFirst();
        long dayCount = world.getFullTime() / 24000;
        long time = world.getTime();
        boolean isStormy = world.hasStorm();

        long hours = (time / 1000 + 6) % 24; // Adjusted to start from 06:00
        long minutes = (time % 1000) * 60 / 1000;

        String timeicon = (hours >= 6 && hours < 18) ? "\u2600" : "\u263D";
        String thunderEmoji = "\u26C8"; // Thunder emoji
        String rainEmoji = "\uD83C\uDF27"; // Rain emoji
        String cloudEmoji = "\u2601"; // Cloud emoji
        String weathericon = isStormy ? thunderEmoji : (world.isClearWeather() ? cloudEmoji : rainEmoji);
        NamedTextColor lineColor = NamedTextColor.WHITE;
        NamedTextColor playColor = ColorUtil.fromHexString("#4454dd");
        NamedTextColor boldColor = ColorUtil.fromHexString("#ffc5ff");
        NamedTextColor xyzColor = ColorUtil.fromHexString("#b289b2");
        NamedTextColor resetColor = NamedTextColor.WHITE;
        String playText = "  \u1D18\u029F\u1D00\u028F"; // "play"
        String xyzText = "x\u028F\u1D22  "; // "xyz"
        String spacerL = "                \u2192";
        String spacerR = "\u2190                 ";

        String timeString = String.format("%02d:%02d", hours, minutes);
        String formattedLine1 = spacerR + resetColor + playColor + playText + boldColor + "HYNSE" + xyzColor + xyzText + playColor + spacerL + resetColor;
        String formattedLine2 = lineColor + timeicon + " Day: " + dayCount + " - " + weathericon + " Time: " + timeString;

        // Center-align both lines
        formattedLine1 = centerAlign(formattedLine1);
        formattedLine2 = centerAlign(formattedLine2);

        event.motd(Component.text(formattedLine1 + "\n" + formattedLine2));
    }

    private String centerAlign(String text) {
        int totalSpaces = Math.max(0, 64 - text.length());
        int leftSpaces = totalSpaces / 2;
        int rightSpaces = totalSpaces - leftSpaces;
        return " ".repeat(leftSpaces) + text + " ".repeat(rightSpaces);
    }
}
