package sourcecoded.core;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import sourcecoded.core.util.JustForFun;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
public class SourceCodedCore {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
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


