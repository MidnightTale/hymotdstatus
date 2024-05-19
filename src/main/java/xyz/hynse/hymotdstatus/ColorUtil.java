package xyz.hynse.hymotdstatus;

import net.kyori.adventure.text.format.NamedTextColor;

public class ColorUtil {

    public static NamedTextColor fromHexString(String hexString) {
        // Remove '#' if it exists at the beginning of the string
        if (hexString.startsWith("#")) {
            hexString = hexString.substring(1);
        }

        // Parse the hexadecimal string to an integer value
        int colorValue = Integer.parseInt(hexString, 16);

        // Create a NamedTextColor instance using the parsed integer value
        return NamedTextColor.namedColor(colorValue);
    }
}
