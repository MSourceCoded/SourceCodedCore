package sourcecoded.core.util;

import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * Mostly just String modifiers
 */
public class LocalizationUtils {

    /**
     * Prefixes a String, with ':' as a separator
     */
    public static String prefix(String prefix, String name) {
        return String.format("%s:%s", prefix, name);
    }

    /**
     * Attempts StatCollector.translateToLocal. If there is
     * no translation, it will return the defaultValue
     * param
     */
    public static String translateWithDefault(String unlocalized, String defaultValue) {
        String ret = StatCollector.translateToLocal(unlocalized);
        if (ret.equals(unlocalized)) return defaultValue; else return ret;
    }

}
