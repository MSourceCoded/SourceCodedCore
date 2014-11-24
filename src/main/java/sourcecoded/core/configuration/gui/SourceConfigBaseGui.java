package sourcecoded.core.configuration.gui;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import sourcecoded.core.configuration.SCConfigManager;

public class SourceConfigBaseGui extends GuiConfig {

    public SourceConfigBaseGui(GuiScreen parentScreen) {
        super(parentScreen, SourceConfigGuiFactory.createElements(SCConfigManager.getConfig()), "sourcecodedcore", false, false, "SourceCodedCore");
    }

}
