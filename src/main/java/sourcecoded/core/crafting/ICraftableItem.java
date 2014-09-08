package sourcecoded.core.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;

/**
 * An interface implemented on the Item
 * object to signify to the ItemRegistry
 * that this item has a crafting recipe
 */
public interface ICraftableItem extends ICraftable {

    public IRecipe[] getRecipes(Item item);

}
