package sourcecoded.core.client.renderer.block;

import net.minecraft.tileentity.TileEntity;

/**
 * Calls back a TileEntity before it is used
 * in a TileRenderProxy, allowing you
 * to change it's TE data before rendering.
 *
 * @see sourcecoded.core.client.renderer.block.SimpleTileRenderProxy
 * @see sourcecoded.core.client.renderer.block.AdvancedTileRenderProxy
 */
public interface IBlockRenderHook {

    public void callbackInventory(TileEntity tile);

}
