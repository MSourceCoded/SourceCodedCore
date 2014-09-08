package sourcecoded.core.util;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import org.lwjgl.opengl.GL11;

/**
 * Mod makers can have fun too, right?
 */
public class JustForFun {

    public int rTimer = 0;
    public boolean rForward = true;

    public int gTimer = 128;
    public boolean gForward = true;

    public int bTimer = 255;
    public boolean bForward = true;

    public boolean cow = false;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void preRenderEntity(RenderLivingEvent.Pre event) {
        EntityLivingBase entity = event.entity;

        String name = EnumChatFormatting.getTextWithoutFormattingCodes(entity.getCommandSenderName());

        cow = name.equalsIgnoreCase("sourcecoded") && entity instanceof EntityCow;

        if (name.equalsIgnoreCase("mrsourcecoded") && entity instanceof EntityPlayer || cow) {
            if (rTimer == 0) rForward = true; else if (rTimer == 255) rForward = false;
            if (gTimer == 0) gForward = true; else if (gTimer == 255) gForward = false;
            if (bTimer == 0) bForward = true; else if (bTimer == 255) bForward = false;

            if (rForward) rTimer++; else rTimer--;
            if (gForward) gTimer++; else gTimer--;
            if (bForward) bTimer++; else bTimer--;

            GL11.glColor4f((float) rTimer / 255, (float) gTimer / 255, (float) bTimer / 255, 1F);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void postRenderEntity(RenderLivingEvent.Post event) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
    }

}
