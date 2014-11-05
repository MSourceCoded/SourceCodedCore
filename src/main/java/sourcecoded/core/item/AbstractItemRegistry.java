package sourcecoded.core.item;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import sourcecoded.core.crafting.CraftingRegistration;
import sourcecoded.core.crafting.ICraftableItem;
import sourcecoded.core.util.CommonUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * An Item registration class for you to extend and use
 * for your own mods
 */
public abstract class AbstractItemRegistry {

    public HashMap<String, Item> itemMap = new HashMap<String, Item>();

    @SuppressWarnings("unchecked")
    /**
     * Registers all the Items added in the
     * addAll() method.
     *
     * Call this in preInit/Init.
     */
    public void registerAll() {
        addAll();

        HashMap<String, Item> cloned = (HashMap<String, Item>) itemMap.clone();

        Iterator<Map.Entry<String, Item>> it = cloned.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Item> pairs = it.next();

            Item theItem = pairs.getValue();

            GameRegistry.registerItem(theItem, pairs.getKey());

            if (CommonUtils.isClient() && theItem instanceof IItemHasRenderer)
                MinecraftForgeClient.registerItemRenderer(theItem, ((IItemHasRenderer) theItem).getRenderer(theItem));

            it.remove();
        }

        doCraftingRegistration();
    }

    @SuppressWarnings("unchecked")
    public void doCraftingRegistration() {
        HashMap<String, Item> cloned = (HashMap<String, Item>) itemMap.clone();

        Iterator<Map.Entry<String, Item>> it = cloned.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Item> pairs = it.next();

            Item theItem = pairs.getValue();

            if (theItem instanceof ICraftableItem)
                CraftingRegistration.register(((ICraftableItem) theItem).getRecipes(theItem));

            it.remove();
        }
    }

    /**
     * Add an Item to the registry
     *
     * @param name The unlocalized name to give the Item
     */
    public AbstractItemRegistry addItem(String name, Item itemOBJ) {
        itemMap.put(name, itemOBJ);
        return this;
    }

    /**
     * Returns an Item matching the name given
     */
    public Item getItemByName(String name) {
        return itemMap.get(name);
    }

    /**
     * Called when Items are ready to be added
     */
    public abstract void addAll();
}
