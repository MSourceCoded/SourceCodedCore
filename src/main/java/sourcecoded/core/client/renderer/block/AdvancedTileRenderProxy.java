package sourcecoded.core.client.renderer.block;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import sourcecoded.core.client.renderer.tile.TESRStaticHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * Provides a means to render a TileEntity as an item.
 * This is very similar to SimpleTileProxy, but instead
 * allows for 'static rendering'.
 *
 * Static rendering allows you to render a block (in world)
 * once per chunk render update (block placed, destroyed, etc).
 * This removes the need to render once per RenderTick, allowing
 * you to render high-vertex models.
 *
 * In short, if you want a block to render only when it needs to,
 * return renderID in the getRenderType method in the Block object,
 * and have it's TileEntity extends TESRStaticHandler.
 *
 * Make sure your Block implements ITileEntityProvider.
 *
 * @see sourcecoded.core.client.renderer.block.SimpleTileRenderProxy
 * @see sourcecoded.core.client.renderer.tile.TESRStaticHandler
 */
public class AdvancedTileRenderProxy implements ISimpleBlockRenderingHandler {

    public static final int renderID = RenderingRegistry.getNextAvailableRenderId();

    TileEntity te;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderBlocks) {
        if (block instanceof ITileEntityProvider) {
            ITileEntityProvider prov = (ITileEntityProvider) block;
            te = prov.createNewTileEntity(null, metadata);
        } else return;

        if (block instanceof IBlockRenderHook) {
            IBlockRenderHook hook = (IBlockRenderHook) block;
            hook.callbackInventory(te);
        }

        glRotatef(90F, 0F, 1F, 0F);
        glTranslatef(-0.5F, -0.5F, -0.5F);
        float scale = 1F;
        glScalef(scale, scale, scale);

        TESRStaticHandler renderer = (TESRStaticHandler) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(te.getClass());
        renderer.renderTile(te, 0.0, 0.0, 0.0, 0, true);

        renderer.renderTile(te, 0.0, 0.0, 0.0, 0, false);

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderBlocks) {
        if (block instanceof ITileEntityProvider) {
            ITileEntityProvider prov = (ITileEntityProvider) block;
            te = prov.createNewTileEntity(null, world.getBlockMetadata(x, y, z));
        } else return false;

        if (world.getTileEntity(x, y, z) != null)
            te = world.getTileEntity(x, y, z);

        Tessellator.instance.draw();
        TESRStaticHandler renderer = (TESRStaticHandler) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(te.getClass());
        renderer.renderTile(te, x, y, z, 0, true);
        Tessellator.instance.startDrawingQuads();

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return renderID;
    }
}
