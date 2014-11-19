package sourcecoded.core.proxy;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import sourcecoded.core.client.renderer.SCRenderManager;
import sourcecoded.core.client.renderer.block.AdvancedTileRenderProxy;
import sourcecoded.core.client.renderer.block.SimpleTileRenderProxy;
import sourcecoded.core.client.settings.Keybindings;
import sourcecoded.core.gameutility.screenshot.ScreenshotShareCommand;
import sourcecoded.core.gameutility.screenshot.ScreenshotTickHandler;
import sourcecoded.core.util.JustForFun;
import sourcecoded.core.version.VersionAlertHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends ServerProxy {
    @Override
    public void registerKeybindings() {
        Keybindings.keyScreenshot = new KeyBinding("Take Screenshot", Keyboard.KEY_F2, "SourceCodedCore");
        ClientRegistry.registerKeyBinding(Keybindings.keyScreenshot);
    }

    @Override
    public void registerClientMisc() {
        FMLCommonHandler.instance().bus().register(new ScreenshotTickHandler());
        FMLCommonHandler.instance().bus().register(new VersionAlertHandler());
        ClientCommandHandler.instance.registerCommand(new ScreenshotShareCommand());
    }

    @Override
    public void registerRenderers() {
        RenderingRegistry.registerBlockHandler(new SimpleTileRenderProxy());
        RenderingRegistry.registerBlockHandler(new AdvancedTileRenderProxy());
        MinecraftForge.EVENT_BUS.register(new JustForFun());
        FMLCommonHandler.instance().bus().register(new SCRenderManager());
    }
}
