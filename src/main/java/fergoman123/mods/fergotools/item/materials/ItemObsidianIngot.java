package fergoman123.mods.fergotools.item.materials;

import fergoman123.mods.fergotools.lib.strings.ItemStrings;
import fergoman123.mods.fergotools.util.base.ItemFT;

public class ItemObsidianIngot extends ItemFT{

    public static ItemObsidianIngot instance = new ItemObsidianIngot();

    public ItemObsidianIngot()
    {
        super();
        this.setMaxStackSize(64);
        this.setUnlocalizedName(ItemStrings.ingotObsidian);
    }
}
