package sourcecoded.core.configuration;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

/**
 * A simplified version of Configuration files
 * with shortcuts to common methods
 *
 * @see net.minecraftforge.common.config.Configuration
 */
public class SourceConfig {

    public Configuration config;

    public File configFile;

    /**
     * Create a new Configuration
     */
    public SourceConfig(File configFile) {
        this.config = new Configuration(configFile);
        this.configFile = configFile;
        this.loadConfig();
    }

    /**
     * Load the config from file
     */
    public void loadConfig() {
        config.load();
    }

    /**
     * Save and reload the config
     */
    public void saveConfig() {
        config.save();
        loadConfig();
    }

    /**
     * Get a specified property
     */
    public Property getProperty(String category, String property) {
        return config.getCategory(category).get(property);
    }

    /**
     * Integer
     */
    public Property createProperty(String category, String name, int def) {
        return config.get(category, name, def);
    }

    public int getInteger(String category, String propertyName) {
        return getProperty(category, propertyName).getInt();
    }

    /**
     * Boolean
     */
    public Property createProperty(String category, String name, boolean def) {
        return config.get(category, name, def);
    }

    public boolean getBool(String category, String propertyName) {
        return getProperty(category, propertyName).getBoolean();
    }

    /**
     * Double
     */
    public Property createProperty(String category, String name, double def) {
        return config.get(category, name, def);
    }

    public double getDouble(String category, String propertyName) {
        return getProperty(category, propertyName).getDouble();
    }

    /**
     * String
     */
    public Property createProperty(String category, String name, String def) {
        return config.get(category, name, def);
    }

    public String getString(String category, String propertyName) {
        return getProperty(category, propertyName).getString();
    }

    public void setComment(String category, String property, String comment) {
        Property prop = getProperty(category, property);
        prop.comment = comment;
    }


}
