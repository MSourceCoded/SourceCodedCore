package sourcecoded.core.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipe;

/**
 * An interface implemented on the Block
 * object to signify to the BlockRegistry
 * that this block has a crafting recipe
 */
public interface ICraftableBlock extends ICraftable {

    public IRecipe[] getRecipes(Block block);

}
