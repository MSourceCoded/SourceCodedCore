package sourcecoded.core.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

/**
 * Implemented on Block classes to signify
 * to the BlockRegistry that this block
 * should be registered with a custom
 * ItemBlock as opposed to the default
 */
public interface IBlockHasItem {

    public Class<? extends ItemBlock> getItemBlock(Block block);

}
