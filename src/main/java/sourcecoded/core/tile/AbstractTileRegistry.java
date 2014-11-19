package sourcecoded.core.tile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import sourcecoded.core.util.CommonUtils;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * A TileEntity registration class for you to extend and use
 * for your own mods
 */
public abstract class AbstractTileRegistry {

    public HashMap<String, Class<? extends TileEntity>> tileMap = new HashMap<String, Class<? extends TileEntity>>();
    public HashMap<String, TileEntitySpecialRenderer> TESR = new HashMap<String, TileEntitySpecialRenderer>();

    @SuppressWarnings("unchecked")
    /**
     * Registers all the TileEntities added in the
     * addAll() method.
     *
     * Call this in preInit/Init.
     */
    public void registerAll() {
        addAll();

        HashMap<String, Class<? extends TileEntity>> cloned = (HashMap<String, Class<? extends TileEntity>>) tileMap.clone();

        Iterator<Map.Entry<String, Class<? extends TileEntity>>> it = cloned.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Class<? extends TileEntity>> pairs = it.next();

            Class<? extends TileEntity> tile = pairs.getValue();

            if (TESR.containsKey(pairs.getKey()) && CommonUtils.isClient())
                ClientRegistry.bindTileEntitySpecialRenderer(tile, TESR.get(pairs.getKey()));

            GameRegistry.registerTileEntity(pairs.getValue(), pairs.getKey());
            it.remove();
        }
    }

    /**
     * Add a tile to the registry
     *
     * @param name The unlocalized name to give the
     *             TileEntity
     */
    public AbstractTileRegistry addTile(String name, Class<? extends TileEntity> tileOBJ) {
        tileMap.put(name, tileOBJ);
        return this;
    }

    /**
     * Add a renderer for a TileEntity
     * registered with tileName
     *
     * @param tileName The name given to the TileEntity
     *                 in addTile()
     */
    public AbstractTileRegistry addRenderer(String tileName, TileEntitySpecialRenderer renderer) {
        TESR.put(tileName, renderer);
        return this;
    }

    /**
     * Returns a tile matching the name given
     */
    public Class<? extends TileEntity> getTileByName(String name) {
        return tileMap.get(name);
    }

    /**
     * Called when TileEntities are ready to be added
     */
    public abstract void addAll();
}
