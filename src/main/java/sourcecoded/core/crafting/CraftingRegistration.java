package sourcecoded.core.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.crafting.IRecipe;

public class CraftingRegistration {

    public static void register(IRecipe[] recipes) {
        for (IRecipe current : recipes)
            GameRegistry.addRecipe(current);
    }

}
