package sourcecoded.core.configuration;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import sourcecoded.core.util.SourceLogger;

/**
 * A configuration class extending SourceConfig
 * used for keeping track of the Configuration's
 * version. Useful for keeping track of Config
 * options during Mod updates
 *
 * @see sourcecoded.core.configuration.SourceConfig
 */
public class VersionConfig extends SourceConfig {

    public static SourceLogger configLogger = new SourceLogger("SourceCodedCore-Configuration");
    public static String CATEGORY_VERSION = "version";

    String targetVersion;
    String modid;

    private VersionConfig(File configFile) {
        super(configFile);
    }

    /**
     * Create a new Config
     */
    public static VersionConfig createNewVersionConfig(File configFile, String targetVersion, String modid) throws IOException {
        VersionConfig config = new VersionConfig(configFile);
        config.modid = modid;
        config.targetVersion = targetVersion;

        return setupVersioning(config);
    }

    private static VersionConfig setupVersioning(VersionConfig config) throws IOException {

        String currentVersion = config.getString(CATEGORY_VERSION, "ConfigRevision");

        if (!currentVersion.equals(config.targetVersion)) {
            configLogger.error(String.format("Configuration File for Mod: %s is out of date! (target %s, found %s). Trying to move the old configuration file to %s_old", config.modid, config.targetVersion, currentVersion, config.configFile.getName()));

            File target = new File(config.configFile.getAbsolutePath() + "_old");

            if (target.exists()) {
                target.delete();
            }

            try {
                FileUtils.moveFile(config.configFile, target);
            } catch (IOException e) {
                configLogger.error("Failed to move the old configuration file to %s" + target.getName());
                return config;
            }

            config.createProperty(CATEGORY_VERSION, "ConfigRevision", config.targetVersion).set(config.targetVersion);
            config.config.addCustomCategoryComment(CATEGORY_VERSION, "Used for Configuration Version Control. DO NOT CHANGE THIS.");
            config.saveConfig();
        }

        return config;
    }
}
