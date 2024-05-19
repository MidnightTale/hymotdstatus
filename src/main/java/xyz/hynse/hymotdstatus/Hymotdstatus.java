package xyz.hynse.hymotdstatus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
        World world = Bukkit.getWorlds().getFirst(); // Using the first world, adjust as needed
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

        // Colors
        Style line2Style = Style.style().color(TextColor.fromHexString("#ffffff")).build(); // Line 2 color
        Style line1_0Style = Style.style().color(TextColor.fromHexString("#4454dd")).build(); // Line 1 color
        Style line1_1Style = Style.style().color(TextColor.fromHexString("#ffc5ff")).build(); // Line 1 color
        Style line1_2Style = Style.style().color(TextColor.fromHexString("#b289b2")).build(); // Line 1 color

        // Components
        Component playText = Component.text("  \u1d18\u029f\u1d00\u028f").style(line1_2Style);
        Component netText = Component.text("\u0274\u1D07\u1D1B  ").style(line1_2Style);
        Component spacerL = Component.text("                \u2192").style(line1_0Style);
        Component spacerR = Component.text("\u2190                 ").style(line1_0Style);

        String timeString = String.format("%02d:%02d", hours, minutes);

        Component formattedLine1 = Component.text()
                .append(spacerL)
                .append(playText)
                .append(Component.text("."))
                .append(Component.text("HYNSE").color(TextColor.fromHexString("#ffc5ff")).decorate(TextDecoration.BOLD))
                .append(Component.text("."))
                .append(netText)
                .append(spacerR)
                .build();

        Component formattedLine2 = Component.text()
                .append(Component.text(timeicon).color(TextColor.fromHexString("#ffffff")))
                .append(Component.text(" Day: " + dayCount + " - ").color(TextColor.fromHexString("#ffffff")))
                .append(Component.text(weathericon).color(TextColor.fromHexString("#ffffff")))
                .append(Component.text(" Time: " + timeString).color(TextColor.fromHexString("#ffffff")))
                .build();

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
