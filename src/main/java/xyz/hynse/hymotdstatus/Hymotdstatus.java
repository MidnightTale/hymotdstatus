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
        // -*- coding: utf-8 -*-

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
            String line2color = String.valueOf(net.md_5.bungee.api.ChatColor.of("#ffffff"));
            String line1_0color = String.valueOf(net.md_5.bungee.api.ChatColor.of("#4454dd"));
            String line1_1color = String.valueOf(net.md_5.bungee.api.ChatColor.of("#ffc5ff"));
            String line1_2color = String.valueOf(net.md_5.bungee.api.ChatColor.of("#b289b2"));
            String bold = ChatColor.BOLD.toString();
            String italic = ChatColor.ITALIC.toString();
            String reset = ChatColor.RESET.toString();
            String strikethrough = ChatColor.STRIKETHROUGH.toString();
            String playText = "  \u1d18\u029f\u1d00\u028f"; // "play"
            String xyzText = "x\u028f\u1d22  "; // "xyz"
            String square = "\u2588";
            String spacerL = "                \u2192";
            String spacerR = "\u2190                 ";

            String timeString = String.format("%02d:%02d", hours, minutes);
            String formattedLine1 = line1_0color + strikethrough + spacerL + reset + line1_2color + playText + "." + line1_1color + bold + "HYNSE" + reset + line1_2color + "." + xyzText + line1_0color + strikethrough + spacerR + reset;
            String formattedLine2 = line2color + timeicon + " Day: " + dayCount + " - " + weathericon + " Time: " + timeString;

            // Center-align both lines
            formattedLine1 = centerAlign(formattedLine1, 64);
            formattedLine2 = centerAlign(formattedLine2, 64);

            motdLines[0] = formattedLine1;
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
    private String convertHexToGradient(String text) {
        String[] hexCodes = text.split("-");
        StringBuilder gradientText = new StringBuilder();

        String previousColor = null;

        for (String hexCode : hexCodes) {
            String color = String.valueOf(net.md_5.bungee.api.ChatColor.of("#" + hexCode.toLowerCase()));
            if (previousColor == null) {
                gradientText.append(color);
            } else {
                gradientText.append(previousColor).append(hexColorTransition(previousColor, color));
            }

            previousColor = color;
        }

        return gradientText.toString();
    }

    private String hexColorTransition(String startColor, String endColor) {
        ChatColor reset = ChatColor.RESET;
        String[] colors = new String[]{String.valueOf(net.md_5.bungee.api.ChatColor.values())};

        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == startColor) {
                int nextIndex = (i + 1) % colors.length;
                String nextColor = colors[nextIndex];
                if (nextColor == endColor) {
                    return nextColor.toString();
                }
            }
        }

        return reset.toString();
    }

}
