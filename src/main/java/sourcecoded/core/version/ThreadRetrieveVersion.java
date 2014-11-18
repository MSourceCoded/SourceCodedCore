package sourcecoded.core.version;

import sourcecoded.core.util.SourceLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.Buffer;

public class ThreadRetrieveVersion extends Thread {

    VersionChecker checker;

    SourceLogger logger;

    public ThreadRetrieveVersion(VersionChecker checker) {
        setName(checker.modid + " Version Checker");
        logger = new SourceLogger(checker.modid + " Version Checker");
        setDaemon(true);

        this.checker = checker;

        start();
    }

    public void run() {
        try {
            URL url = new URL(checker.versionURL);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            checker.onlineUnparsed = reader.readLine();
            reader.close();

            checker.checked = true;
            checker.onCheckComplete();
        } catch (IOException e) {
            logger.warn("Version Checker encountered Exception: " + e.getClass());
            e.printStackTrace();
        }
    }

}
