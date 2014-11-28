package sourcecoded.core.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import sourcecoded.core.client.renderer.SCRenderManager;
import sourcecoded.core.client.renderer.block.AdvancedTileRenderProxy;
import sourcecoded.core.client.renderer.block.SimpleTileRenderProxy;
import sourcecoded.core.client.settings.Keybindings;
import sourcecoded.core.configuration.SCConfigManager;
import sourcecoded.core.gameutility.screenshot.ScreenshotShareCommand;
import sourcecoded.core.gameutility.screenshot.ScreenshotTickHandler;
import sourcecoded.core.util.JustForFun;
import sourcecoded.core.version.VersionAlertHandler;
import sourcecoded.core.version.VersionCommand;

public class ClientProxy extends ServerProxy {
    @Override
    public EntityPlayer getClientPlayer() {
        return FMLClientHandler.instance().getClientPlayerEntity();
    }

    @Override
    public void registerKeybindings() {
        if (SCConfigManager.getBoolean(SCConfigManager.Properties.SCREENSHOT_ENABLED)) {
            Keybindings.keyScreenshot = new KeyBinding("Take Screenshot", Keyboard.KEY_F2, "SourceCodedCore");
            ClientRegistry.registerKeyBinding(Keybindings.keyScreenshot);
        }
    }

    @Override
    public void registerClientMisc() {
        if (SCConfigManager.getBoolean(SCConfigManager.Properties.SCREENSHOT_ENABLED)) {
            FMLCommonHandler.instance().bus().register(new ScreenshotTickHandler());
            ClientCommandHandler.instance.registerCommand(new ScreenshotShareCommand());
        }

        ClientCommandHandler.instance.registerCommand(new VersionCommand());

        FMLCommonHandler.instance().bus().register(new VersionAlertHandler());
    }

    @Override
    public void registerRenderers() {
        RenderingRegistry.registerBlockHandler(new SimpleTileRenderProxy());
        RenderingRegistry.registerBlockHandler(new AdvancedTileRenderProxy());
        FMLCommonHandler.instance().bus().register(new SCRenderManager());

        MinecraftForge.EVENT_BUS.register(new JustForFun());
    }
}