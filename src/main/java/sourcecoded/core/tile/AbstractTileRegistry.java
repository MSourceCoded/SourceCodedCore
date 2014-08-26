package sourcecoded.core.tile;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractTileRegistry {

    public HashMap<String, Class<? extends TileEntity>> tileMap = new HashMap<String, Class<? extends TileEntity>>();

    @SuppressWarnings("unchecked")
    public void registerAll() {
        addAll();

        HashMap<String, Class<? extends TileEntity>> cloned = (HashMap<String, Class<? extends TileEntity>>) tileMap.clone();

        Iterator<Map.Entry<String, Class<? extends TileEntity>>> it = cloned.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Class<? extends TileEntity>> pairs = it.next();
            GameRegistry.registerTileEntity(pairs.getValue(), pairs.getKey());
            it.remove();
        }
    }

    public void addTile(String name, Class<? extends TileEntity> tileOBJ) {
        tileMap.put(name, tileOBJ);
    }

    public Class<? extends TileEntity> getTileByName(String name) {
        return tileMap.get(name);
    }

    public abstract void addAll();
}
