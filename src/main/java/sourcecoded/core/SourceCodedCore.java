package sourcecoded.core;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.CoreModManager;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
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
import sourcecoded.core.version.VersionAlertHandler;
import sourcecoded.core.version.VersionChecker;
import sourcecoded.core.version.VersionCommand;

import java.io.IOException;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
public class SourceCodedCore {

    public static KeyBinding keyScreenshot = new KeyBinding("Take Screenshot", 60, "SourceCodedCore");

    public static VersionChecker checker;

    public static boolean isDevEnv = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        isDevEnv = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
        SCConfigManager.init(VersionConfig.createNewVersionConfig(event.getSuggestedConfigurationFile(), "0.2", Constants.MODID));

        if (SCConfigManager.getBoolean(SCConfigManager.Properties.VERS_ON))
            checker = new VersionChecker(Constants.MODID, "https://raw.githubusercontent.com/MSourceCoded/SourceCodedCore/master/version/{MC}.txt", Constants.VERSION, SCConfigManager.getBoolean(SCConfigManager.Properties.VERS_AUTO), SCConfigManager.getBoolean(SCConfigManager.Properties.VERS_SILENT));

        if (event.getSide() == Side.CLIENT)
            FMLCommonHandler.instance().bus().register(new VersionAlertHandler());

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
        if (!isDevEnv && checker != null)
            checker.check();
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new VersionCommand());
    }
}


