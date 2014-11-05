package sourcecoded.core;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.RegistryNamespaced;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import sourcecoded.core.client.renderer.SCRenderManager;
import sourcecoded.core.client.renderer.block.AdvancedTileRenderProxy;
import sourcecoded.core.client.renderer.block.SimpleTileRenderProxy;
import sourcecoded.core.configuration.SCConfigManager;
import sourcecoded.core.configuration.VersionConfig;
import sourcecoded.core.gameutility.screenshot.ScreenshotShareCommand;
import sourcecoded.core.gameutility.screenshot.ScreenshotTickHandler;
import sourcecoded.core.util.CommonUtils;
import sourcecoded.core.util.JustForFun;
import sourcecoded.core.util.SourceLogger;

import java.io.IOException;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
public class SourceCodedCore {

    public static KeyBinding keyScreenshot = new KeyBinding("Take Screenshot", 60, "SourceCodedCore");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        SCConfigManager.init(VersionConfig.createNewVersionConfig(event.getSuggestedConfigurationFile(), "0.2", Constants.MODID));

        if (CommonUtils.isClient()) {
            RenderingRegistry.registerBlockHandler(new SimpleTileRenderProxy());
            RenderingRegistry.registerBlockHandler(new AdvancedTileRenderProxy());
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new JustForFun());

            FMLCommonHandler.instance().bus().register(new ScreenshotTickHandler());

            Minecraft mc = Minecraft.getMinecraft();
            mc.gameSettings.setOptionKeyBinding(mc.gameSettings.keyBindScreenshot, SCConfigManager.getInteger(SCConfigManager.Properties.SCREENSHOT_STANDARD));

            ClientRegistry.registerKeyBinding(keyScreenshot);

            ClientCommandHandler.instance.registerCommand(new ScreenshotShareCommand());
        }

        FMLCommonHandler.instance().bus().register(new SCRenderManager());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}


