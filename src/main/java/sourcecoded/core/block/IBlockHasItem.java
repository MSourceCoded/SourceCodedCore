package sourcecoded.core.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public interface IBlockHasItem {

    public Class<? extends ItemBlock> getItemBlock(Block block);

}
