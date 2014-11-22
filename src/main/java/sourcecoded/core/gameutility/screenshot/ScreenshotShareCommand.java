package sourcecoded.core.gameutility.screenshot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class ScreenshotShareCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "screenshot";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/screenshot <imgur>";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, final String[] args) {
        if (args.length > 0 && SCScreenshotHandler.mostRecentScreenshot != null) {
            if (args[0].equalsIgnoreCase("imgur")) {
                sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Upload started...."));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String response = null;
                        try {
                            if (args.length == 1)
                                response = getImgurContent("bcbfecd69a6eca7", null);
                            else if (args.length == 2)
                                response = getImgurContent("bcbfecd69a6eca7", args[1]);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (Minecraft.getMinecraft().thePlayer != null) {
                            if (response != null) {
                                JsonObject obj = new JsonParser().parse(response).getAsJsonObject();
                                JsonObject data = obj.getAsJsonObject("data");
                                boolean success = obj.get("success").getAsBoolean();
                                String url = data.get("link").getAsString();

                                if (success) {
                                    String str = "[{\"text\":\"Imgur upload successful, link copied to clipboard! \",\"color\":\"white\"}"
                                            + ",{\"text\":\"" + url + "\",\"color\":\"aqua\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":"
                                            + "{\"text\":\"Click to open the link\",\"color\":\"yellow\"}}"
                                            + ",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + url + "\"}}]";
                                    IChatComponent chat = IChatComponent.Serializer.func_150699_a(str);
                                    Minecraft.getMinecraft().thePlayer.addChatMessage(chat);
                                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                                    clipboard.setContents(new StringSelection(url), null);
                                } else {
                                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Imgur upload failed! :c (failed)"));
                                }
                            } else {
                                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Imgur upload failed! :c (no response)"));
                            }
                        }
                    }
                }).start();
            }
        } else if (args.length == 0) {
            throw new WrongUsageException(getCommandUsage(sender));
        } else {
            throw new WrongUsageException("You have to take a screenshot first!");
        }
    }

    public String getImgurContent(String clientID, String file) throws Exception {
        URL url;
        url = new URL("https://api.imgur.com/3/image");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //String data = encodeImageToB64(SCScreenshotHandler.mostRecentScreenshot);
        String data = null;

        if (file != null)
            data = encodeImageToB64(new File(SCScreenshotHandler.screenshotDir, file));
        else
            data = encodeImageToB64(SCScreenshotHandler.mostRecentScreenshot);

        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Client-ID " + clientID);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

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

    public String encodeImageToB64(File file) throws IOException {
        BufferedImage image = null;
        image = ImageIO.read(file);
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArray);
        byte[] byteImage = byteArray.toByteArray();
        String dataImage = Base64.encodeBase64String(byteImage);

        return URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(dataImage, "UTF-8");
    }

    @SuppressWarnings("rawtypes")
    public List addTabCompletionOptions(ICommandSender sender, String[] str) {
        if (str.length == 1) return getListOfStringsMatchingLastWord(str, "imgur");
        return null;
    }
}
