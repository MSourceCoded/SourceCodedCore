package sourcecoded.core.crafting;

import net.minecraft.item.crafting.IRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingRegistration {

    public static void register(IRecipe[] recipes) {
        for (IRecipe current : recipes)
            GameRegistry.addRecipe(current);
    }

}
