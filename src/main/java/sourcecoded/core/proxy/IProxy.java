package sourcecoded.core.proxy;

import net.minecraft.entity.player.EntityPlayer;

public interface IProxy
{
    public EntityPlayer getClientPlayer();

    public void registerKeybindings();

    public void registerRenderers();

    public void registerClientMisc();
}
