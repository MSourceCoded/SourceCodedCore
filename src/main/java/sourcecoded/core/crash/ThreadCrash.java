package sourcecoded.core.crash;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import sourcecoded.core.SourceCodedCore;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ThreadCrash extends Thread {

    List<String> packageIDs;

    public ThreadCrash(List<String> packageIDs) {
        setName("SourceCodedCore -- Crash Reporter");
        this.packageIDs = packageIDs;
    }

    @Override
    public void run() {
        File dirPath = new File(SourceCodedCore.getForgeRoot(), "crash-reports");
        File report = lastFileModified(dirPath);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(report));

            boolean sccModInvolved = false;

            ArrayList<String> lines = new ArrayList<String>();
            String lastLine;
            lines.add("-----------------------------------------------------------------------------------------------------------");
            lines.add("You are reading this because your game has crashed.");
            lines.add("The cause of this crash was detected as a mod that uses SourceCodedCore.");
            lines.add("Please send this to SourceCoded in the appropriate issue tracker. All relevant information is listed below.");
            lines.add("-----------------------------------------------------------------------------------------------------------");
            while ((lastLine = reader.readLine()) != null) {
                if (!sccModInvolved) {
                    for (String packageID : packageIDs)
                        if (lastLine.trim().startsWith("at") && lastLine.contains(packageID)) {
                            sccModInvolved = true;
                        }
                }

                lines.add(lastLine);
            }

            reader.close();

            if (sccModInvolved) {
                String response = uploadGist(lines);
                response = parseResponse(response);
                Desktop.getDesktop().browse(new URI(response));
            }
        } catch (Exception e) {
            System.err.println("Could not upload Crash Report to Gist");
        }
    }

    public static File lastFileModified(File fl) {
        File[] files = fl.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        if (files != null) {
            long lastMod = Long.MIN_VALUE;
            File choice = null;
            for (File file : files) {
                if (file.lastModified() > lastMod) {
                    choice = file;
                    lastMod = file.lastModified();
                }
            }
            return choice;
        }
        return null;
    }

    public static String uploadGist(List<String> lines) throws IOException {
        URL url = new URL("https://api.github.com/gists");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        String data = null;
        data = encodeFile(lines);

        conn.connect();
        StringBuilder stb = new StringBuilder();
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            stb.append(line).append("\n");
        }
        wr.close();
        rd.close();

        return stb.toString();
    }

    public static String encodeFile(List<String> lines) throws IOException {
        StringWriter sw = new StringWriter();
        JsonWriter writer = new JsonWriter(sw);

        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            builder.append(line).append("\n");
        }
        String details = builder.toString();

        writer.beginObject();

        writer.name("public");
        writer.value(true);

        writer.name("description");
        writer.value("SourceCodedCore crash report");

        writer.name("files");
        writer.beginObject();
        writer.name("RawCrashLog.txt");
        writer.beginObject();
        writer.name("content");
        writer.value(details);
        writer.endObject();
        writer.endObject();

        writer.endObject();

        writer.close();

        return sw.toString();
    }

    public static String parseResponse(String response) {
        try {
            JsonObject obj = new JsonParser().parse(response).getAsJsonObject();
            return obj.get("html_url").getAsString();
        } catch (Exception e) {
            System.err.println("Could not upload Crash Report to Gist");
        }
        return "";
    }

}
