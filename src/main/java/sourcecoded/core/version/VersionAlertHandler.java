package sourcecoded.core.version;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class VersionAlertHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        for (VersionChecker checker : VersionChecker.checkers) {
            if (!checker.alerted && checker.newVersionAvailable && !checker.silent) {
                if (checker.downloaded) {
                    event.player.addChatComponentMessage(new ChatComponentText("Mod: " + checker.modid + " has been automatically updated to: " + checker.onlineParsed + " (from " + checker.currentVersion + ")").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
                    event.player.addChatComponentMessage(new ChatComponentText("The new update will be available next time you restart MC").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
                } else {
                    event.player.addChatComponentMessage(new ChatComponentText("Mod: " + checker.modid + " has an update available: " + checker.onlineParsed + " (current: " + checker.currentVersion + ")").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
                    event.player.addChatComponentMessage(new ChatComponentText("Run '/scversion update " + checker.modid + "' to download & update").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
                }
            }

            checker.alerted = true;
        }
    }

}
