package sourcecoded.core.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import sourcecoded.core.crafting.CraftingRegistration;
import sourcecoded.core.crafting.ICraftableItem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractItemRegistry {

    public HashMap<String, Item> itemMap = new HashMap<String, Item>();

    @SuppressWarnings("unchecked")
    public void registerAll() {
        addAll();

        HashMap<String, Item> cloned = (HashMap<String, Item>) itemMap.clone();

        Iterator<Map.Entry<String, Item>> it = cloned.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Item> pairs = it.next();

            Item theItem = pairs.getValue();

            GameRegistry.registerItem(theItem, pairs.getKey());

            if (theItem instanceof ICraftableItem)
                CraftingRegistration.register(((ICraftableItem) theItem).getRecipes(theItem));

            it.remove();
        }
    }

    public void addItem(String name, Item itemOBJ) {
        itemMap.put(name, itemOBJ);
    }

    public Item getItemByName(String name) {
        return itemMap.get(name);
    }

    public abstract void addAll();
}
