package io.github.fergoman123.fergotools.item.hoe;

import io.github.fergoman123.fergotools.init.ModItems;
import io.github.fergoman123.fergotools.reference.names.ItemNames;
import io.github.fergoman123.fergotools.reference.names.Locale;
import io.github.fergoman123.fergotools.util.item.Materials;
import io.github.fergoman123.fergotools.util.tool.ItemHoeFT;
import io.github.fergoman123.fergoutil.helper.NameHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemAdamantiumHoe extends ItemHoeFT {

    public ItemAdamantiumHoe() {
        super(Materials.Tools.adamantium, Materials.Tools.adamantium.getMaxUses());
        this.setUnlocalizedName(ItemNames.adamantiumHoe);
    }

    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.isItemEqual(new ItemStack(ModItems.ingotAdamantium)) || super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean useExtraInfo)
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        {
            list.add(NameHelper.translateToLocal(Locale.adamantiumToolTip));
            list.add(NameHelper.translateToLocal(Locale.durabilityToolTip) + " Infinity");
        }
        else
        {
            list.add(NameHelper.translateToLocal(Locale.holdShiftMessage));
        }
    }

}
