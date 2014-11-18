package sourcecoded.core.version;

import cpw.mods.fml.relauncher.FMLInjectionData;
import org.apache.commons.compress.compressors.FileNameUtil;
import sourcecoded.core.util.SourceLogger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ThreadDownloadVersion extends Thread {

    String fileName;

    VersionChecker checker;
    SourceLogger logger;

    public ThreadDownloadVersion(VersionChecker checker) {
        setName(checker.modid + " Version Checker Downloader");
        logger = new SourceLogger(checker.modid + " Version Checker Downloader");
        setDaemon(true);

        this.checker = checker;

        start();
    }

    public void run() {
        try {
            URL url = new URL(checker.downloadURL);

            String MCPath = ((File)(FMLInjectionData.data()[6])).getAbsolutePath().replace(File.separatorChar, '/').replace("/.", "");
            String modsPath = MCPath + "/mods";

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
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

            File oldFile = new File(savePath.replace(checker.onlineParsed, checker.currentVersion));

            if (oldFile.exists())
                oldFile.delete();

            checker.downloaded = true;
            checker.onDownloadComplete();
        } catch (IOException e) {
            logger.warn("Version Checker Downloader encountered Exception: " + e.getClass());
            e.printStackTrace();
        }

    }
}
