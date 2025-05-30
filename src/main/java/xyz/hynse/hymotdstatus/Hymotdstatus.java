package xyz.hynse.hymotdstatus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hymotdstatus extends JavaPlugin implements Listener {

    private final MiniMessage miniMessage = MiniMessage.builder().build();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        World world = Bukkit.getWorlds().getFirst(); // Using the first world, adjust as needed
        long dayCount = world.getFullTime() / 24000;
        long time = world.getTime();
        boolean isStormy = world.hasStorm();

        long hours = (time / 1000 + 6) % 24; // Adjusted to start from 06:00
        long minutes = (time % 1000) * 60 / 1000;

        String timeicon = (hours >= 6 && hours < 18) ? "☀" : "☽";
        String thunderEmoji = "⛈"; // Thunder emoji
        String rainEmoji = "\uD83C\uDF27"; // Rain emoji
        String cloudEmoji = "☁"; // Cloud emoji ☰
        String weathericon = isStormy ? thunderEmoji : (world.isClearWeather() ? cloudEmoji : rainEmoji);

        Component formattedLine1 = miniMessage.deserialize("<gradient:#FF0000:#FF7F00:#FFFF00:#00FF00:#0000FF:#BB6CF5:#D1A4FF><bold>           </bold></gradient> <#4c53d9>ᴘʟᴀʏ.<bold><#6f75fc>VOXELSTELLAR</bold>.<#4c53d9>ɴᴇᴛ <gradient:#D1A4FF:#BB6CF5:#0000FF:#00FF00:#FFFF00:#FF7F00:#FF0000><bold>           </bold></gradient>");
        Component formattedLine2 = miniMessage.deserialize("<#5357ad>" + timeicon + " Day: " + dayCount + " - " + weathericon + "Time: " + String.format("%02d:%02d", hours, minutes));
        // Center-align line 2
        formattedLine2 = centerAlign(formattedLine2);

        event.motd(Component.text()
                .append(formattedLine1)
                .append(Component.newline())
                .append(formattedLine2)
                .build());
    }

    private Component centerAlign(Component text) {
        String content = Component.text().content();
        int totalSpaces = Math.max(0, 30 - content.length());
        int leftSpaces = totalSpaces / 2;
        int rightSpaces = totalSpaces - leftSpaces;
        String spaces = " ".repeat(leftSpaces);
        return Component.text(spaces).append(text).append(Component.text(" ".repeat(rightSpaces)));
    }
}
