package sourcecoded.core.configuration.gui;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import sourcecoded.core.configuration.SCConfigManager;

public class SourceConfigBaseGui extends GuiConfig {

    static String modid;
    static String name;
    static SourceConfig config;
    static List<IConfigElement> elements;

    public SourceConfigBaseGui(GuiScreen parentScreen) {
        super(parentScreen, elements, modid, false, false, modid);
    }
    
    public static void injectGuiContext(SourceConfigGuiFactory factory) {
        this.modid = factory.modid;
        this.name = this.modid;
        this.config = factory.config;
        this.elements = SourceConfigGuiFactory.createElements(factory.config);
    }

}
