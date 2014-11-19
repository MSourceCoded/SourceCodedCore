package sourcecoded.core.util;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.model.obj.WavefrontObject;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Mod makers can have fun too, right?
 */
public class JustForFun {

    WavefrontObject sonic = new WavefrontObject(new ResourceLocation("sourcecodedcore", "sonic.obj"));
    WavefrontObject belt = new WavefrontObject(new ResourceLocation("sourcecodedcore", "belt.obj"));
    ResourceLocation blank = new ResourceLocation("sourcecodedcore", "blank.png");
    ResourceLocation noise = new ResourceLocation("sourcecodedcore", "noise.png");

    public boolean shouldRenderAddon(EntityLivingBase entity) {
        String name = EnumChatFormatting.getTextWithoutFormattingCodes(entity.getCommandSenderName());
        return name.equals("SourceJaci") || name.equals("MrSourceCoded");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void renderSource(RenderPlayerEvent.Specials.Pre event) {
        EntityLivingBase entity = event.entityPlayer;

        if (shouldRenderAddon(entity)) {
            GL11.glPushMatrix();

            event.renderer.modelBipedMain.bipedBody.postRender(0.0625F);

            GL11.glEnable(GL11.GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            GL11.glColor4f(1F, 1F, 1F, 1F);

            Minecraft.getMinecraft().renderEngine.bindTexture(blank);

            Tessellator tess = Tessellator.instance;

            GL11.glTranslatef(0.3F, 0.68F, -0.2F);

            GL11.glRotatef(75F, 1F, 0F, 0F);
            GL11.glRotatef(45F, 0F, 1F, 0F);

            GL11.glScalef(0.7F, 0.7F, 0.7F);

            GL11.glColor4f(0.6F, 0.5F, 0.3F, 0.9F);
            tess.startDrawing(GL11.GL_TRIANGLES);
            tess.setBrightness(140);
            sonic.tessellatePart(tess, "InnerShaft");
            tess.draw();

            GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);
            tess.startDrawing(GL11.GL_TRIANGLES);
            sonic.tessellatePart(tess, "Outer_Shaft");
            sonic.tessellatePart(tess, "Arms");
            tess.draw();

            GL11.glColor4f(0.2F, 1F, 0.2F, 0.5F);
            tess.startDrawing(GL11.GL_TRIANGLES);
            tess.setBrightness(240);
            sonic.tessellatePart(tess, "Head");
            tess.draw();

            GL11.glColor4f(0.1F, 0.1F, 0.1F, 1F);
            tess.startDrawing(GL11.GL_TRIANGLES);
            sonic.tessellatePart(tess, "Upper_Handle");
            sonic.tessellatePart(tess, "Lower_Handle");
            tess.draw();

            GL11.glColor4f(0.15F, 0.15F, 0.15F, 1F);
            tess.startDrawing(GL11.GL_TRIANGLES);
            sonic.tessellatePart(tess, "HandleShaft");
            tess.draw();

            GL11.glColor4f(0.05F, 0.05F, 0.05F, 1F);
            tess.startDrawing(GL11.GL_TRIANGLES);
            sonic.tessellatePart(tess, "Binding");
            tess.draw();

            GL11.glPopMatrix();

            GL11.glPushMatrix();

            event.renderer.modelBipedMain.bipedBody.postRender(0.0625F);

            Minecraft.getMinecraft().renderEngine.bindTexture(noise);

            GL11.glScalef(0.063F, 0.063F, 0.063F);

            GL11.glRotatef(90F, 0F, -1F, 0F);
            GL11.glTranslatef(0F, 9F, 0F);

            GL11.glColor4f(0.18F, 0.1F, 0.12F, 1F);
            tess.startDrawing(GL11.GL_TRIANGLES);
            belt.tessellateAllExcept(tess, "Hex");
            tess.draw();

            GL11.glColor4f(0.8F, 0.2F, 0.8F, 1F);
            tess.startDrawing(GL11.GL_TRIANGLES);
            tess.setBrightness(200);
            belt.tessellatePart(tess, "Hex");
            tess.draw();

            GL11.glPopMatrix();

        }
    }

}
