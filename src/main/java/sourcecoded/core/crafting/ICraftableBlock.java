package sourcecoded.core.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipe;

public interface ICraftableBlock extends ICraftable {

    public IRecipe[] getRecipes(Block block);

}
