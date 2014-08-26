package sourcecoded.core.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import sourcecoded.core.crafting.CraftingRegistration;
import sourcecoded.core.crafting.ICraftableBlock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractBlockRegistry {

    public HashMap<String, Block> blockMap = new HashMap<String, Block>();

    @SuppressWarnings("unchecked")
    public void registerAll() {
        addAll();

        HashMap<String, Block> cloned = (HashMap<String, Block>) blockMap.clone();

        Iterator<Map.Entry<String, Block>> it = cloned.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Block> pairs = it.next();

            Block theBlock = pairs.getValue();

            if (theBlock instanceof IBlockHasItem)
                GameRegistry.registerBlock(theBlock, ((IBlockHasItem) theBlock).getItemBlock(theBlock), pairs.getKey());
            else
                GameRegistry.registerBlock(theBlock, pairs.getKey());

            if (theBlock instanceof ICraftableBlock)
                CraftingRegistration.register(((ICraftableBlock) theBlock).getRecipes(theBlock));

            it.remove();
        }
    }

    public void addBlock(String name, Block blockOBJ) {
        blockMap.put(name, blockOBJ);
    }

    public Block getBlockByName(String name) {
        return blockMap.get(name);
    }

    public abstract void addAll();
}
