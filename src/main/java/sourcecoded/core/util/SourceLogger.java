package sourcecoded.core.util;

import static org.apache.logging.log4j.Level.ALL;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.FATAL;
import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

/**
 * An Objectified logger
 */
public class SourceLogger {

    public String modid;

    public SourceLogger(String modid) {
        this.modid = modid;
    }

    public void log(Level logLevel, Object logObj) {
        FMLLog.log(modid, logLevel, String.valueOf(logObj));
    }

    public void all(Object logObj) {
        log(ALL, logObj);
    }

    public void fatal(Object logObj) {
        log(FATAL, logObj);
    }

    public void error(Object logObj) {
        log(ERROR, logObj);
    }

    public void warn(Object logObj) {
        log(WARN, logObj);
    }

    public void info(Object logObj) {
        log(INFO, logObj);
    }

}
