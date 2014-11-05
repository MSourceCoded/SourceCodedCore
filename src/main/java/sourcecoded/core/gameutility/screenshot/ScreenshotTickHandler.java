package sourcecoded.core.gameutility.screenshot;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;
import sourcecoded.core.SourceCodedCore;
import sourcecoded.core.configuration.SCConfigManager;

import javax.sql.rowset.serial.SerialRef;

public class ScreenshotTickHandler {

    @SubscribeEvent
    public void keyEvent(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (SourceCodedCore.keyScreenshot.isPressed()) {
            IChatComponent data = SCScreenshotHandler.saveScreenshot(mc.mcDataDir, mc.displayWidth, mc.displayHeight, mc.getFramebuffer());
            if (SCConfigManager.getBoolean(SCConfigManager.Properties.SCREENSHOT_MESSAGE)) mc.ingameGUI.getChatGUI().printChatMessage(data);
        }
    }

}
