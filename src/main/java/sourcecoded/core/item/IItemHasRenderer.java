package sourcecoded.core.item;

import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;

/**
 * An interface to implement on your Items
 * that will indicate to the ItemRegistry that
 * they have a Custom Renderer
 */
public interface IItemHasRenderer {

    /**
     * The renderer instance to use for the item
     */
    public IItemRenderer getRenderer(Item item);

}
