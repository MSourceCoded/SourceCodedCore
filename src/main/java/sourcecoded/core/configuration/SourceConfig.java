package sourcecoded.core.configuration;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class SourceConfig {

    public Configuration config;

    public File configFile;

    public SourceConfig(File configFile) {
        this.config = new Configuration(configFile);
        this.configFile = configFile;
    }

    public void loadConfig() {
        config.load();
    }

    public void saveConfig() {
        config.save();
        loadConfig();
    }

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
        Property prop = getProperty(category, propertyName);
        return prop.getInt();
    }

    /**
     * Boolean
     */
    public Property createProperty(String category, String name, boolean def) {
        return config.get(category, name, def);
    }

    public boolean getBool(String category, String propertyName) {
        Property prop = getProperty(category, propertyName);
        return prop.getBoolean();
    }

    /**
     * Double
     */
    public Property createProperty(String category, String name, double def) {
        return config.get(category, name, def);
    }

    public double getDouble(String category, String propertyName) {
        Property prop = getProperty(category, propertyName);
        return prop.getDouble();
    }

    /**
     * String
     */
    public Property createProperty(String category, String name, String def) {
        return config.get(category, name, def);
    }

    public String getString(String category, String propertyName) {
        Property prop = getProperty(category, propertyName);
        return prop.getString();
    }

    public void setComment(String category, String property, String comment) {
        Property prop = getProperty(category, property);
        prop.comment = comment;
    }


}
