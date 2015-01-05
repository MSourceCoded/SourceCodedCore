package sourcecoded.core.configuration;

import org.apache.commons.io.FileUtils;
import sourcecoded.core.util.SourceLogger;

import java.io.File;
import java.io.IOException;

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
        config.createProperty(CATEGORY_VERSION, "ConfigRevision", config.targetVersion);
        config.saveConfig();

        config.config.addCustomCategoryComment(CATEGORY_VERSION, "Used for Configuration Version Control. DO NOT CHANGE THIS.");
        String currentVersion = config.getString(CATEGORY_VERSION, "ConfigRevision");

        if (!currentVersion.equals(config.targetVersion)) {
            configLogger.error(String.format("Configuration File for Mod: %s is out of date! (target %s, found %s). The old configuration has been moved to /configOld/%s_old", config.modid, config.targetVersion, currentVersion, config.configFile.getName()));

            //File newDir = new File(config.configFile.getPath() + "/../../configOld");
            File newDir = new File(config.configFile.getParentFile().getParentFile(), "configOld");
            File target = new File(newDir + "/" + config.configFile.getName() + "_old");

            if (target.exists()) target.delete();
            FileUtils.moveFile(config.configFile, target);

            VersionConfig config1 = createNewVersionConfig(config.configFile, config.targetVersion, config.modid);
            return config1;
        }

        return config;
    }
}
