package sourcecoded.core.configuration.gui;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import sourcecoded.core.configuration.SourceConfig;

import java.util.List;

public class SourceConfigBaseGui extends GuiConfig {

    static String modid;
    static String name;
    static SourceConfig config;
    static List<IConfigElement> elements;

    public SourceConfigBaseGui(GuiScreen parentScreen) {
        super(parentScreen, elements, modid, false, false, modid);
    }
    
    public static void injectGuiContext(SourceConfigGuiFactory factory) {
        modid = factory.modid;
        name = modid;
        config = factory.config;
        elements = SourceConfigGuiFactory.createElements(factory.config);
    }

}
