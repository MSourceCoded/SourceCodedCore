package sourcecoded.core.version;

import sourcecoded.core.SourceCodedCore;

import java.io.File;
import java.io.IOException;

public class ThreadTrashRemover extends Thread {

    File fileToRemove;

    public static File cleanupDir = new File(SourceCodedCore.getForgeRoot() + "/mods/sourcecodedcore");

    public ThreadTrashRemover(File file) {
        this.fileToRemove = file;
        this.setName("SourceCodedCore-Version-Cleanup-Hook");
    }

    public void run() {
        SourceCodedCore.logger.info("Launching Version Cleanup Daemon for File: " + fileToRemove.getName());
        try {
            Process cleanupProcess = Runtime.getRuntime().exec("java -jar " + cleanupDir + "/Cleanup.jar " + fileToRemove.getAbsolutePath());
            SourceCodedCore.logger.info("Version Cleanup Launched");
        } catch (IOException e) {
            SourceCodedCore.logger.warn("Version Cleanup Failed. Exception Log Trailing. Remove file manually.");
            e.printStackTrace();
        }
    }

    public static void initCleanup() throws IOException {
        if (!cleanupDir.exists())
            cleanupDir.mkdir();

        File file = new File(cleanupDir + "/Cleanup.jar");

        if (!file.exists()) {
            new ThreadDownloadCleanup();
        }
    }

    public static void inject(File file) {
        Runtime.getRuntime().addShutdownHook(new ThreadTrashRemover(file));
    }

}
