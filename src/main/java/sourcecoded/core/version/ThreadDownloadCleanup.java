package sourcecoded.core.version;

import sourcecoded.core.util.SourceLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ThreadDownloadCleanup extends Thread {

    String fileName;

    SourceLogger logger;

    public ThreadDownloadCleanup() {
        setName("Version Cleanup Downloader");
        logger = new SourceLogger("Version Cleaner Downloader");
        setDaemon(true);

        start();
    }

    public void run() {
        try {
            URL url = new URL("http://dl.sourcecoded.info/Cleanup.jar");

            String modsPath = ThreadTrashRemover.cleanupDir.getAbsolutePath();

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            con.connect();
            int response = con.getResponseCode();

            if (response != HttpURLConnection.HTTP_OK) {
                con.disconnect();
                throw new IOException("Invalid HTTP Response: " + response);
            }

            String filename;
            URL contentURL = con.getURL();

            filename = contentURL.toString().substring(contentURL.toString().lastIndexOf("/") + 1);

            String savePath = modsPath + "/" + filename;

            File save = new File(savePath);
            save.createNewFile();

            FileOutputStream stream = new FileOutputStream(save.getAbsolutePath());
            InputStream inputStream = con.getInputStream();

            int bytesRead = -1;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                stream.write(buffer, 0, bytesRead);
            }

            stream.close();
            inputStream.close();
            con.disconnect();
        } catch (IOException e) {
            logger.warn("Version Cleanup Downloader encountered Exception: " + e.getClass());
            e.printStackTrace();
        }

    }
}
