package sourcecoded.core.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;

public interface ICraftableItem extends ICraftable {

    public IRecipe[] getRecipes(Item item);

}
