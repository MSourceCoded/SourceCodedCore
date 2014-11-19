package sourcecoded.core.proxy;

import net.minecraft.entity.player.EntityPlayer;

public class ServerProxy extends CommonProxy {
    @Override
    public EntityPlayer getClientPlayer() {
        return null;
    }

    @Override
    public void registerKeybindings() {
    }

    @Override
    public void registerRenderers() {
    }

    @Override
    public void registerClientMisc() {
    }
}
