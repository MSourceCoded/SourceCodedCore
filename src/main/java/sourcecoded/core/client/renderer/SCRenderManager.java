package sourcecoded.core.client.renderer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class SCRenderManager {

    public static float lastPartialTick;

    @SubscribeEvent
    public void renderEvent(TickEvent.RenderTickEvent event) {
        lastPartialTick = event.renderTickTime;
    }

}
