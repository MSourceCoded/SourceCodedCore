package sourcecoded.core.version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import sourcecoded.core.SourceCodedCore;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class VersionChecker {

    public static ArrayList<VersionChecker> checkers = new ArrayList<VersionChecker>();

    public String versionURL;
    public String currentVersion;
    public String modid;

    public String onlineUnparsed;
    public boolean checked = false;
    public boolean downloaded = false;

    public boolean newVersionAvailable = false;

    public String onlineParsed;
    public String downloadURL;

    public boolean autoUpdate;

    public boolean alerted = false;

    public boolean silent = false;

    public VersionChecker(String modid, String url, String currentVersion, boolean autoUpdate, boolean silent) {
        this.modid = modid.replace(" ", "_");

        url = url.replace("{MC}", MinecraftForge.MC_VERSION);

        this.versionURL = url;
        this.currentVersion = currentVersion;

        this.autoUpdate = autoUpdate;
        this.silent = silent;

        checkers.add(this);
    }

    public void check() {
        new ThreadRetrieveVersion(this);
    }

    public void onCheckComplete() {
        parse();

        if (onlineParsed.equalsIgnoreCase(currentVersion)) return;      //Versions Equal

        List<String> compare = new ArrayList<String>();
        compare.add(onlineParsed);
        compare.add(currentVersion);

        Collections.sort(compare);

        if (compare.get(1).equals(onlineParsed)) {          //New Version
            newVersionAvailable = true;
            if (autoUpdate)
                new ThreadDownloadVersion(this);
        }

    }

    public void parse() {
        String[] split = onlineUnparsed.split("@@");
        onlineParsed = split[0];
        downloadURL = split[1];
    }

    public void onDownloadComplete() {
        if (FMLCommonHandler.instance().getSide() != Side.CLIENT) return;
        if (silent) return;

        EntityPlayer player = SourceCodedCore.proxy.getClientPlayer();
        if (player != null) {
            player.addChatComponentMessage(new ChatComponentText("Download of: " + modid + " @ " + onlineParsed + " is complete! Restart MC to apply changes!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
        }
    }

}
