package sourcecoded.core.block;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import sourcecoded.core.crafting.CraftingRegistration;
import sourcecoded.core.crafting.ICraftableBlock;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * A Block registration class for you to extend and use
 * for your own mods
 */
public abstract class AbstractBlockRegistry {

    public HashMap<String, Block> blockMap = new HashMap<String, Block>();

    @SuppressWarnings("unchecked")
    /**
     * Registers all the Blocks added in the
     * addAll() method.
     *
     * Call this in preInit/Init.
     */
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

            it.remove();
        }

        doCraftingRegistration();
    }

    @SuppressWarnings("unchecked")
    public void doCraftingRegistration() {
        HashMap<String, Block> cloned = (HashMap<String, Block>) blockMap.clone();

        Iterator<Map.Entry<String, Block>> it = cloned.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Block> pairs = it.next();

            Block theBlock = pairs.getValue();

            if (theBlock instanceof ICraftableBlock)
                CraftingRegistration.register(((ICraftableBlock) theBlock).getRecipes(theBlock));

            it.remove();
        }
    }

    /**
     * Add a Block to the registry
     *
     * @param name The unlocalized name to give the Item
     */
    public AbstractBlockRegistry addBlock(String name, Block blockOBJ) {
        blockMap.put(name, blockOBJ);
        return this;
    }

    /**
     * Returns a Block matching the name given
     */
    public Block getBlockByName(String name) {
        return blockMap.get(name);
    }

    /**
     * Called when Blocks are ready to be added
     */
    public abstract void addAll();
}
