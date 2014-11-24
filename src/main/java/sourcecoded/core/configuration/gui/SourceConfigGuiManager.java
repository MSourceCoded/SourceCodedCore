package sourcecoded.core.configuration.gui;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SourceConfigGuiManager {

    @SubscribeEvent
    public void configChanged(ConfigChangedEvent event) {
        SourceConfigGuiFactory factory = SourceConfigGuiFactory.get(event.modID);
        if (factory != null)
            factory.config.saveConfig();
    }

}
