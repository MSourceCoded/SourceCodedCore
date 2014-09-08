package sourcecoded.core;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.RegistryNamespaced;
import net.minecraftforge.common.MinecraftForge;
import sourcecoded.core.client.renderer.block.AdvancedTileRenderProxy;
import sourcecoded.core.client.renderer.block.SimpleTileRenderProxy;
import sourcecoded.core.util.CommonUtils;
import sourcecoded.core.util.JustForFun;
import sourcecoded.core.util.SourceLogger;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
public class SourceCodedCore {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (CommonUtils.isClient()) {
            RenderingRegistry.registerBlockHandler(new SimpleTileRenderProxy());
            RenderingRegistry.registerBlockHandler(new AdvancedTileRenderProxy());
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            MinecraftForge.EVENT_BUS.register(new JustForFun());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}


